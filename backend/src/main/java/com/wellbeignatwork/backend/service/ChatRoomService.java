package com.wellbeignatwork.backend.service;


import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.exceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.repository.ChatRoomRepository;
import com.wellbeignatwork.backend.repository.MessageRepository;
import com.wellbeignatwork.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChatRoomService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate, MessageRepository messageRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
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


    @Transactional
    public void roomBasedChat(Message message, Long roomId, Long senderId) throws MessagingException {
        User sender = userRepository
                .findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("user with id :" + senderId + "does not exist"));
        ChatRoom chatRoom = chatRoomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("chatRoom with id :" + roomId + "does not exist"));
        message.setChatroom(chatRoom);
        message.setSender(sender);


        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
    }

    public void publicChat(Message message) {
        messagingTemplate.convertAndSend("/topic/message",message);
    }

}
