package com.wellbeignatwork.backend.service;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.exceptions.chatExceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import com.wellbeignatwork.backend.repository.ChatRoomRepository;
import com.wellbeignatwork.backend.repository.MessageRepository;
import com.wellbeignatwork.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ChatRoomService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final PushNotificationService notificationService;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository
            , UserRepository userRepository
            , SimpMessagingTemplate messagingTemplate
            , MessageRepository messageRepository
            , PushNotificationService notificationService) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.notificationService = notificationService;
    }


    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public void deleteChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.delete(chatRoom);
    }

    public ChatRoom updateChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
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
                    if (chatRoom1.getUsers().size() > chatRoom1.getUsers().size() && chatRoom1.getMessages().size() > chatRoom2.getMessages().size())
                        return 1;
                    else return 0;
                });

        return chatRooms;

    }






    @Transactional
   // @Scheduled(fixedRate = 10000)
    public void calculateResponseRatePerRoom() {

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


                }


            }
        });
    }


    @Transactional
    public void addUserToChatRoom(@NotNull Long chatRoomId, @NotNull Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id :" + userId + "does not exist"));
        chatRoomRepository
                .findById(chatRoomId)
                .map((chatRoom -> chatRoom.getUsers().add(user)))
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom with id :" + chatRoomId + "does not exist"));
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



    public void oneToOneChat(Message message, Long senderId, Long recieverId) throws FirebaseMessagingException {

        //get the sender and the reciever first
        User sender = userRepository
                .findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id : " + senderId + "does not exist"));
        User reciever = userRepository
                .findById(recieverId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id : " + recieverId + "does not exist"));

        //check if there is a unique room for the 2 users to communicate else create one for them
        ChatRoom room1 = chatRoomRepository.findByUniqueKey(String.format("%s_%s", senderId, recieverId));
        ChatRoom room2 = chatRoomRepository.findByUniqueKey(String.format("%s_%s", recieverId, senderId));
        if (room1 != null || room2 != null) {
            messagingTemplate.convertAndSend("/topic/room/" + String.format("%s_%s", senderId, recieverId), message);
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
            ChatRoom room = new ChatRoom();
            room.setUniqueKey(String.format("%s_%s", sender.getId(), reciever.getId()));
            Set<User> users = new HashSet<>();
            users.add(sender);
            users.add(reciever);
            room.setUsers(users);
            chatRoomRepository.save(room);
            message.setSender(sender);
            message.setChatroom(room);
            messagingTemplate.convertAndSend("/topic/room/" + room.getUniqueKey(), message);

            //subscribe all users in the chatRoom to the specific notification topic
            List<String> subscriptionTokens = new ArrayList<>();
            room.getUsers().forEach(user -> subscriptionTokens.add(user.getFireBaseToken()));
            notificationService.subScribeUsersToTopic(subscriptionTokens, String.format("room_%s", room.getUniqueKey()));
            //notify all room users that a new message have been sent
            notificationService.sendToTopic(new PushNotificationRequest(room.getRoomName(), "received a message", String.format("room_%s", room.getUniqueKey())));


        }
    }


    public void roomBasedChat(Message message, Long roomId, Long senderId) throws MessagingException, FirebaseMessagingException {


        //extract the sender and the chatroom of the messsage
        User sender = userRepository
                .findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id :" + senderId + "does not exist"));
        ChatRoom chatRoom = chatRoomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom with id :" + roomId + "does not exist"));
        message.setChatroom(chatRoom);
        message.setSender(sender);
        log.info("mess sent is : {}", message.getChatroom().getRoomName());

        //send the message to the message broker to be handled and sent the client

        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);

        //subscribe all users in the chatRoom to the specific notification topic
        List<String> subscriptionTokens = new ArrayList<>();
        chatRoom.getUsers().forEach(user -> {
            if(user.getFireBaseToken()!=null){
                subscriptionTokens.add(user.getFireBaseToken());
            }

        });
        notificationService.subScribeUsersToTopic(subscriptionTokens, String.format("room_%s", roomId));

        //notify all room users that a new message have been sent
        notificationService.sendToTopic(new PushNotificationRequest(chatRoom.getRoomName(), "received a message from " + sender.getUserName(), String.format("room_%s", roomId)));

    }

    public void publicChat(Message message) {
        messagingTemplate.convertAndSend("/topic/message", message);
    }

}
