package com.wellbeignatwork.backend.service.ChatService;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import com.wellbeignatwork.backend.entity.Chat.Message;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface IChatService {

    public ChatRoom createChatRoom(ChatRoom chatRoom);
    public void deleteChatRoom(ChatRoom chatRoom);
    public ChatRoom updateChatRoom(ChatRoom chatRoom);
    public List<ChatRoom> getAllRooms();
    public List<ChatRoom> getPublicRooms();
    public List<ChatRoom> getMostActiveChatRooms();
    public void calculateResponseRatePerRoom();
    public void addUserToChatRoom(@NotNull Long chatRoomId, @NotNull Long userId);
    public void removeUserFromChatRoom(Long chatRoomId, Long userId);
    public void oneToOneChat(Message message, Long senderId, Long recieverId) throws FirebaseMessagingException;
    public void roomBasedChat(Message message, Long roomId, Long senderId) throws MessagingException, FirebaseMessagingException;
    public void publicChat(Message message);
}