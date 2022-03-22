package com.wellbeignatwork.backend.service;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.exceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import com.wellbeignatwork.backend.repository.ChatRoomRepository;
import com.wellbeignatwork.backend.repository.MessageRepository;
import com.wellbeignatwork.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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


        //send the message to the message broker to be handled and sent the client

        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);

        //subscribe all users in the chatRoom to the specific notification topic
        List<String> token = new ArrayList<>();
        chatRoom.getUsers().forEach(user -> token.add(user.getFireBaseToken()));
        notificationService.subScribeUsersToTopic(token, String.format("room_%s", roomId));

        //notify all room users that a new message have been sent
        notificationService.sendToTopic(new PushNotificationRequest(chatRoom.getRoomName(), "received a message from " + sender.getUserName(), String.format("room_%s", roomId)));

    }

    public void publicChat(Message message) {
        messagingTemplate.convertAndSend("/topic/message", message);
    }

}
