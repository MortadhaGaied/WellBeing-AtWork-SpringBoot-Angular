package com.wellbeignatwork.backend.service.ChatService;


import antlr.debug.MessageAdapter;
import bsh.util.JConsole;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import com.wellbeignatwork.backend.entity.Chat.Message;
import com.wellbeignatwork.backend.entity.Chat.Notification;
import com.wellbeignatwork.backend.entity.Chat.NotificationType;
import com.wellbeignatwork.backend.entity.Event.Event;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.exceptions.Event.BadRequestException;
import com.wellbeignatwork.backend.exceptions.chatExceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import com.wellbeignatwork.backend.repository.Chat.ChatRoomRepository;
import com.wellbeignatwork.backend.repository.Chat.MessageRepository;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.NotificationService.PushNotificationService;
import com.wellbeignatwork.backend.service.NotificationService.WsNotificationService;
import com.wellbeignatwork.backend.service.UserService.MailService;
import com.wellbeignatwork.backend.util.BadWordFilter;
import com.wellbeignatwork.backend.util.FirebaseStorage;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ChatRoomService implements IChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final PushNotificationService notificationService;
    private final MailService mailService;
    private final FirebaseStorage firebaseStorage;
    private final WsNotificationService WSnotificationService;

    @Autowired
    public ChatRoomService(WsNotificationService WSnotificationService,
            ChatRoomRepository chatRoomRepository
            , UserRepository userRepository
            , SimpMessagingTemplate messagingTemplate
            , MessageRepository messageRepository
            , PushNotificationService notificationService
            , MailService mailService
            , FirebaseStorage firebaseStorage) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.notificationService = notificationService;
        this.mailService = mailService;
        this.firebaseStorage = firebaseStorage;
        this.WSnotificationService = WSnotificationService;
    }


    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        UUID uniqueId = UUID.randomUUID();
        chatRoom.setUniqueKey(uniqueId.toString());
        chatRoom.setCreationDate(new Date());
        return chatRoomRepository.save(chatRoom);
    }

    public void deleteChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.delete(chatRoom);
    }

    public ChatRoom updateChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);


    }

    @Override
    public ChatRoom findRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("room with id " + roomId + " does not exist"));
    }

    public List<ChatRoom> getAllRooms() {
        return chatRoomRepository.findAll();
    }

    public List<ChatRoom> getPublicRooms() {
        List<ChatRoom> allRooms = chatRoomRepository.findAll();
        List<ChatRoom> publicRooms = new ArrayList<>();
        allRooms.forEach(chatRoom -> {
            if (chatRoom.getRoomName() != null) {
                publicRooms.add(chatRoom);
            }
        });
        return publicRooms;
    }


    //chatrooms that have the most users and also that have the most messages
    public List<ChatRoom> getMostActiveChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        chatRooms
                .sort((chatRoom1, chatRoom2) -> {
                    if (chatRoom1.getUsers().size() > chatRoom1.getUsers().size() && chatRoom2.getMessages().size() > chatRoom2.getMessages().size())
                        return 1;
                    else return 0;
                });

        return chatRooms;

    }


    @Transactional
    @Scheduled(fixedRate = 30000)
    public void calculateResponseRatePerRoom() {
        Map<String, String> data = new HashMap<>();
        chatRoomRepository.findAll().forEach(chatRoom -> {
            List<Date> sentAt = new ArrayList<>();
            List<Long> responseTimeDurationsPerRoom = new ArrayList<>();

            if (chatRoom.getMessages().isEmpty()) {
                chatRoom.setAverageResponseTime("not calculated yet");

            } else {
                chatRoom.getMessages().forEach(message -> {

                    sentAt.add(message.getSendAt());
                });

                sentAt.forEach(send -> log.info("dates are here : " + send));

                if (sentAt.size() < 2) {
                    chatRoom.setAverageResponseTime("not calculated yet");
                } else {
                    log.info("here");
                    for (int i = 1; i < sentAt.size(); i++) {
                        Date d1;
                        Date d2;
                        d1 = sentAt.get(i);
                        d2 = sentAt.get(i - 1);
                        long diffMinutes = Math.abs(d2.getTime() - d1.getTime());

                        long diff = TimeUnit.SECONDS.convert(diffMinutes, TimeUnit.MILLISECONDS);

                        responseTimeDurationsPerRoom.add(diff);
                    }

                    var score = responseTimeDurationsPerRoom
                            .stream()
                            .mapToLong(a -> a)
                            .average()
                            .orElse(0);
                    log.info("score is :'(  : " + score);

                    responseTimeDurationsPerRoom.forEach(timeDuration -> {
                        log.info("time duration" + timeDuration);
                    });
                    chatRoom.setAverageResponseTime(Long.toString((long) score));

                    data.put(chatRoom.getRoomName(), Long.toString((long) score));
                }

                Firestore dbFirestore = FirestoreClient.getFirestore();
                dbFirestore.collection("Room-Response-Time").document("room-responseRate").set(data);
            }
        });


    }


    public void addUserToChatRoom(@NotNull Long chatRoomId, @NotNull Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id :" + userId + "does not exist"));


        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom with id :" + chatRoomId + "does not exist"));


        chatRoom.getUsers().add(user);
        chatRoomRepository.save(chatRoom);

    }

    @Transactional
    public void removeUserFromChatRoom(Long chatRoomId, Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id :" + userId + "does not exist"));
        chatRoomRepository
                .findById(chatRoomId)
                .map((chatRoom -> chatRoom.getUsers().remove(user)))
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom with id :" + chatRoomId + "does not exist"));
    }


    public ChatRoom findRoomByUsersAndUniqueKey(Long user1Id, Long user2Id) {

        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new ResourceNotFoundException("user not found "));
        log.info("**************************" + user2.getDisplayName());
        log.info("**************************" + user1.getDisplayName());
        ChatRoom found = null;
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        for (ChatRoom chatRoom : chatRoomRepository.findByRoomNameIsNull()) {
            log.info(String.valueOf(users.size()));
            log.info(String.valueOf(chatRoom.getUsers().size()));
            log.info(String.valueOf(chatRoom.getUsers().contains(user1)));

            if (chatRoom.getUsers().contains(user1) && (chatRoom.getUsers().contains(user2)) && (chatRoom.getUniqueKey() != null)
                    && (chatRoom.getUniqueKey().equals(String.format("%s_%s", user1.getId().toString(), user2.getId().toString()))
                    || chatRoom.getUniqueKey().equals(String.format("%s_%s", user2.getId().toString(), user1.getId().toString())))
            ) {
                found = chatRoom;
            }
        }
        return found;
    }


    public void oneToOneChat(Message message, Long recieverId, Long senderId, String roomUniqueKey) throws FirebaseMessagingException {
        ChatRoom chatRoom = chatRoomRepository.findByUniqueKey(roomUniqueKey);
        //ChatRoom roomExists = findRoomByUsersAndUniqueKey(recieverId,senderId);
        log.info("***********************" + chatRoom.getUniqueKey());
        //get the sender and the reciever first
        User sender = userRepository
                .findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id : " + senderId + "does not exist"));
        User reciever = userRepository
                .findById(recieverId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id : " + recieverId + "does not exist"));

        //check if there is a unique room for the 2 users to communicate else create one for them


        if (chatRoom != null) {
            log.info("entered if statement !!!!!");
            message.setSender(sender);
            message.setChatroom(chatRoom);
            messagingTemplate.convertAndSend("/topic2/room/private/" + chatRoom.getId().toString(), message);

            //subscribe all users in the chatRoom to the specific notification topic
            List<String> subscriptionTokens = new ArrayList<>();
            subscriptionTokens.add(sender.getFireBaseToken());
            subscriptionTokens.add(reciever.getFireBaseToken());
            final String format = String.format("room_%s", String.format("%s_%s", senderId, recieverId));
            notificationService.subScribeUsersToTopic(subscriptionTokens, format);
            //send notifications to subscribed members of the room
            notificationService.sendToTopic(new PushNotificationRequest("hi", "received a message", format));
        }
        //if there is not a unique room for the 2 users to chat then we create one
        else {
            log.info("entered else statement");
            ChatRoom room = new ChatRoom();
            room.setUniqueKey(String.format("%s_%s", reciever.getId(), sender.getId()));
            Set<User> users = new HashSet<>();
            users.add(sender);
            users.add(reciever);
            room.setUsers(users);

            chatRoomRepository.save(room);

            message.setSender(sender);
            message.setChatroom(room);
            messagingTemplate.convertAndSend("/topic2/room/private/" + room.getId().toString(), message);


            //subscribe all users in the chatRoom to the specific notification topic
            List<String> subscriptionTokens = new ArrayList<>();
            room.getUsers().forEach(user -> subscriptionTokens.add(user.getFireBaseToken()));
            notificationService.subScribeUsersToTopic(subscriptionTokens, String.format("room_%s", room.getUniqueKey()));
            //notify all room users that a new message have been sent
            notificationService.sendToTopic(new PushNotificationRequest(room.getRoomName(), "received a message", String.format("room_%s", room.getUniqueKey())));


        }
    }

    public Set<User> helperFunction(ChatRoom room,User sender){
        Set<User> filteredUsers = new HashSet<>(room.getUsers());
        filteredUsers.remove(sender);
        filteredUsers.forEach(user -> log.info(user.getDisplayName()));
        return filteredUsers;
    }

    public void roomBasedChat(Message message, Long roomId, Long senderId) throws MessagingException, FirebaseMessagingException {


        //extract the sender and the chatroom of the messsage
        User sender = userRepository
                .findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id :" + senderId + "does not exist"));
        ChatRoom chatRoom = chatRoomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom with id :" + roomId + "does not exist"));

        //check if sender is banned or is a member in this chatroom
        if(chatRoom.getBannList().contains(sender.getId())){
            log.info("bann list called");
            Notification notification = new Notification();
            notification.setTitle("banned");
            notification.setType(NotificationType.ALERT);
            notification.setBody("you cant send messages you are banned  : "+chatRoom.getRoomName());
            notification.setSentAt(DateTime.now());
            //notification.setData(String.format("{'roomID':%s}",chatRoom.getId()));
            WSnotificationService.dispatch(notification,sender.getId());
            return;
        }
        /*
        if(chatRoom.getUsers().isEmpty()){
            return;
        }*/
        //check if sender does not exist in the room
        if(!chatRoom.getUsers().contains(sender)){
            log.info("you must join the room ");
            Notification notification = new Notification();
            notification.setTitle(chatRoom.getRoomName());
            notification.setType(NotificationType.ALERT);
            notification.setBody("you must join the room to be able to chat  : ");
            notification.setSentAt(DateTime.now());
            WSnotificationService.dispatch(notification,sender.getId());
            return;
        }

       // log.info();
        /*if(!chatRoom.getUsers().contains(sender)){
            throw new MessagingException("user not found in room userList");
        }*/

        int badWordsCount = sender.getBadWordsCount();
        log.info("bad words for user " + sender.getDisplayName() + "are " + sender.getBadWordsCount());
        if (message.getContent().contains("**")) {

            sender.setBadWordsCount(badWordsCount + 1);
            userRepository.save(sender);
        }

/*
        if (chatRoom.getMaxBadWords() <= badWordsCount) {
            autoBannUsersFromChatRooms(badWordsCount, sender, chatRoom);
            //badWordsPerUser.get(sender).clear();
            return;
        }*/

        //send the message to the message broker to be handled and sent the client
        message.setChatroom(chatRoom);
        message.setSender(sender);
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic2/room/" + roomId, message);

        //subscribe all users in the chatRoom to the specific notification topic
        List<String> subscriptionTokens = new ArrayList<>();
        chatRoom.getUsers().forEach(user -> {
            if (user.getFireBaseToken() != null) {
                subscriptionTokens.add(user.getFireBaseToken());
            }

        });
        for (User user:chatRoom.getUsers()){
            log.info("sending notification");
            Notification notification = new Notification();
            notification.setType(NotificationType.MESSAGE);
            notification.setBody("new Message from room : "+chatRoom.getRoomName());
            notification.setSentAt(DateTime.now());
            //notification.setData(String.format("{'roomID':%s}",chatRoom.getId()));
            WSnotificationService.dispatch(notification,user.getId());
        }
        notificationService.subScribeUsersToTopic(subscriptionTokens, String.format("room_%s", roomId));
        //notify all room users that a new message have been sent
        notificationService.sendToTopic(new PushNotificationRequest(chatRoom.getRoomName(), "received a message from " + sender.getDisplayName(), String.format("room_%s", roomId)));

    }

    public void publicChat(Message message, Long senderId) throws FirebaseMessagingException {

        User sender = userRepository.findById(senderId).get();
        message.setSender(sender);
        messagingTemplate.convertAndSend("/topic2/message", message);
        //notify all room users that a new message have been sent
        notificationService.sendPushNotificationToALlUsers(message.getContent(), "public room");
    }

    @Override
    public void inviteUserToChatRoom(Long userId, Long roomId,Long senderId) {


        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("room with this id : " + roomId + " not found")

        );
        User user = userRepository.findById(userId).orElse(null);
        log.info("************user tokenn plssssssssss **********" + user.getDisplayName());
        if (room.getUsers().contains(user)) {
            throw new BadRequestException("You can't invite someone who already in the ChatRoom");
        }
        //prep data

        String redirectionLink = "http://localhost:8081/Wellbeignatwork/chatroom/acceptInvitation/" + roomId + "/" + userId;
        Map<String, String> data = new HashMap<>();
        data.put("redirectionLink", redirectionLink);
        data.put("senderID",senderId.toString());
        log.info(data.get("senderID"));
        //prep request
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("ChatRoom Invitation");
        request.setMessage("you have been invited to Chatroom  : " + room.getRoomName());
        request.setToken(user.getFireBaseToken());
        request.setData(data);
        Notification notification = new Notification();
        notification.setTitle("chatRoom invitation");
        notification.setType(NotificationType.INVITATION);
        notification.setBody("you have been invited to chatroom"+room.getRoomName());
        notification.setSentAt(DateTime.now());
        notification.setData(data);
        WSnotificationService.dispatch(notification,userId);
        //mail sent
        mailService.sendMail(user.getEmail(), "Chat Room Invitation", "you have been invited to ChatRoom : " + room.getRoomName() + "\n" + "click this link to accept the invitation" + "\n" + redirectionLink, false);
        //notification sent

      /*  try {
            notificationService.sendMessageToTokenWithExtraData(request);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
*/

    }

    @Override
    public void acceptInvitation(Long userID, Long roomId,Long invitationSenderId) {
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("ChatRoom Invitation");
        // route to to execute the AssignUserToRoom method

        User invitationSender = userRepository.findById(invitationSenderId)
                .map(user1 -> {
                    log.info("user token here *****************" + user1.getFireBaseToken());

                    return user1;
                })
                .orElseThrow(() -> new com.wellbeignatwork.backend.exceptions.Evaluation.ResourceNotFoundException("user with id : " + userID + " doesnt exist"));


        User user = userRepository.findById(userID)
                .map(user1 -> {
                    log.info("user token here *****************" + user1.getFireBaseToken());
                    request.setToken(user1.getFireBaseToken());
                    return user1;
                })
                .orElseThrow(() -> new com.wellbeignatwork.backend.exceptions.Evaluation.ResourceNotFoundException("user with id : " + userID + " doesnt exist"));


        chatRoomRepository.findById(roomId).map((room -> {

            if (room.getUsers().contains(user)) {
                //send a notification to tell the user that he already joined
                //prepare the request
                request.setMessage("You are Already In this Room ");
                //send decline notification
                notificationService.sendPushNotificationToToken(request);
                /*new PushNotificationRequest("ChatRoom Invitation"
                        ,"You are Already In this Room "
                        ,user.getFireBaseToken()
                        ,null
                )*/
            } else {
                // all things good
                addUserToChatRoom(roomId, userID);
                //acceptence notification
                //prepare the request
                //request.setMessage(" you have been succesfully joined our chatRoom : " + room.getRoomName());
                //notificationService.sendPushNotificationToToken(request);
                Notification notification = new Notification();
                notification.setTitle("invitation accepted");
                notification.setBody("user : "+user.getDisplayName()+" has accepted the room invitation");
                notification.setSentAt(DateTime.now());
                WSnotificationService.dispatch(notification,invitationSenderId);
            }


            return room;
        })).orElseThrow(() -> new com.wellbeignatwork.backend.exceptions.Evaluation.ResourceNotFoundException("Room  with id : " + roomId + " not found"));
    }


    @Override
    public void bannUserFromChatRoom(Long userId, Long roomId) {
        //findTheUser
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        //find TheRoom
        ChatRoom room = chatRoomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("room not found"));
        //break the association user-room
        //room.getUsers().remove(user);

        //add the user to the bannList
        room.getBannList().add(user.getId());
        chatRoomRepository.save(room);

        Notification wsnotification = new Notification();
        wsnotification.setType(NotificationType.ALERT);
        wsnotification.setTitle("banned");
        wsnotification.setBody("you got banned from  "+room.getRoomName());
        wsnotification.setSentAt(DateTime.now());
        WSnotificationService.dispatch(wsnotification,userId);
        //sendNotification
        //prepare the request
        PushNotificationRequest notification = new PushNotificationRequest();
        notification.setTitle("you have been banned ! ");
        notification.setMessage("due to your recent behaviour you have been banned from the room : " + room.getRoomName());
        notification.setToken(user.getFireBaseToken());
        notificationService.sendPushNotificationToToken(notification);
        //send Mail
        mailService.sendMail(user.getEmail(), "user bann", "due to your recent behaviour you have been banned from the room : " + room.getRoomName(), false);
    }

    @Transactional
    @Override
    public void autoBannUsersFromChatRooms(int badWordsFound, User user, ChatRoom room) {
        if (badWordsFound >= room.getMaxBadWords()) {
            room.getUsers().remove(user);
            int currentBannDuration = user.getBanDuration();
            //setting next bann period
            user.setBanDuration(currentBannDuration * 2);
            user.setBannStartDate(new Date());
            //userRepository.save(user);
            //chatRoomRepository.save(room);
            //send notitification
            PushNotificationRequest notification = new PushNotificationRequest();
            notification.setTitle("you have been banned ! ");
            notification.setMessage("sending bad words in the chat got you  you have been banned from the room : " + room.getRoomName());
            notification.setToken(user.getFireBaseToken());
            notificationService.sendPushNotificationToToken(notification);
            //send Mail
            //mailService.sendMail(user.getEmail(), "you have been banned ! ", "sending bad words in the chat got you  you have been banned from the room : " + room.getRoomName(), false);
            //send email
        }

    }

    @Transactional
    @Override
    public void unbannUserFromChatRoom(Long userId, Long roomId) {
        //findTheUser
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        //find TheRoom
        ChatRoom room = chatRoomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("room not found"));
        //re-create the association user-room
        user.setBadWordsCount(0);
        user.setBannStartDate(null);
        room.getBannList().remove(userId);
        Notification notification1 = new Notification();
        notification1.setType(NotificationType.ALERT);
        notification1.setBody("your bann period has been passed now you can go back to tchat in room :  " + room.getRoomName());
        notification1.setTitle("Ban Ended");
        notification1.setSentAt(DateTime.now());
        chatRoomRepository.save(room);
        WSnotificationService.dispatch(notification1,userId);

        //sendNotification
        //prepare the request
        PushNotificationRequest notification = new PushNotificationRequest();
        notification.setTitle("Ban Ended !!  ");
        notification.setMessage("your bann period has been passed now you can go back to tchat in room :  " + room.getRoomName());
        notification.setToken(user.getFireBaseToken());
        notificationService.sendPushNotificationToToken(notification);
        //send Mail
        mailService.sendMail(user.getEmail(), "user unbann", "your bann period has been passed now you can go back to tchat in room :  " + room.getRoomName() + "\n" +
                "keep in mind the next time your bann period will get doubled ! ", false);

    }

    @Override
    public Set<User> findUsersByChatroom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .map(ChatRoom::getUsers)
                .orElseThrow(() -> new ResourceNotFoundException("room with id : " + roomId + "does not exist"));
    }

    @Override
    public void uploadImage(MultipartFile file, Long roomId) {
        chatRoomRepository.findById(roomId).map(chatRoom -> {
            try {
                String roomImage = firebaseStorage.uploadFile(file);
                chatRoom.setImage("https://firebasestorage.googleapis.com/v0/b/" + firebaseStorage.getBUCKETNAME() + "/o/" + roomImage + "?alt=media");
                chatRoomRepository.save(chatRoom);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return chatRoom;
        }).orElseThrow(() -> new ResourceNotFoundException("room with id " + roomId + " not found"));
    }

    @Override
    public boolean checkUserBannedFromRoom(Long userID, Long roomID) {
        boolean result = false;
        User user = userRepository.findById(userID).orElseThrow(()->new ResourceNotFoundException("user not found"));
        ChatRoom room = chatRoomRepository.findById(roomID).orElseThrow(()->new ResourceNotFoundException("room not found"));
        if (room.getBannList().contains(userID)){
            result=true;
        }
        return false;
    }


}
