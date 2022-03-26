package com.wellbeignatwork.backend.controller;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.payload.MessageRequest;
import com.wellbeignatwork.backend.payload.MessageResponse;
import com.wellbeignatwork.backend.payload.PushNotificationResponse;
import com.wellbeignatwork.backend.repository.MessageRepository;
import com.wellbeignatwork.backend.service.ChatRoomService;
import com.wellbeignatwork.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController

public class ChatController {
    private final ChatRoomService chatRoomService;


    private final MessageService messageService;

    @Autowired
    private ChatController(
            ChatRoomService chatRoomService
            , MessageService messageService
    ) {
        this.chatRoomService = chatRoomService;

        this.messageService = messageService;
    }




    @PostMapping("/chatroom/add-room")
    @ResponseBody
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @DeleteMapping("/chatroom/delete-room")
    @ResponseBody
    public void deleteChatRoom(@RequestBody ChatRoom chatRoom) {
        chatRoomService.deleteChatRoom(chatRoom);
    }

    @PutMapping("/chatroom/update-room")
    @ResponseBody
    public ChatRoom updateRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.updateChatRoom(chatRoom);
    }

    @GetMapping("/chatroom/all-rooms")
    @ResponseBody
    @CrossOrigin("*")
    public List<ChatRoom> getAllRooms() {
        return chatRoomService.getAllRooms();
    }

    @GetMapping("/chatroom/all-public-rooms")
    public List<ChatRoom> getPublicRooms() {
        return chatRoomService.getPublicRooms();
    }


    @GetMapping("/chatroom/addUserToRoom/{chatRoomId}/{userId}")
    @ResponseBody
    public void addUserToChatRoom(@NotBlank @Valid @PathVariable Long chatRoomId, @NotBlank @Valid @PathVariable Long userId) {
        chatRoomService.addUserToChatRoom(chatRoomId, userId);
    }

    @GetMapping("/chatroom/removeUserFromRoom/{chatRoomId}/{userId}")
    @ResponseBody
    public void removeUserFromRoom(@NotBlank @Valid @PathVariable Long chatRoomId, @NotBlank @Valid @PathVariable Long userId) {
        chatRoomService.removeUserFromChatRoom(chatRoomId, userId);
    }


    @MessageMapping("/chat/{roomId}/{senderID}")
    public void roomBasedChat(@Payload Message message, @Valid @DestinationVariable Long roomId, @Valid @DestinationVariable Long senderID) throws MessagingException, FirebaseMessagingException {

        chatRoomService.roomBasedChat(messageService.filterBadWords(message), roomId, senderID);

    }


    @MessageMapping("/chat/oneToOne/{senderId}/{recieverId}")
    public void oneToOneChat(@Payload Message message, @Valid @DestinationVariable Long senderId, @Valid @DestinationVariable Long recieverId) throws MessagingException, FirebaseMessagingException {

        chatRoomService.oneToOneChat(messageService.filterBadWords(message), senderId, recieverId);

    }

    @MessageMapping("/chat/public")
    public void publicCHat(@Payload Message message) {
        chatRoomService.publicChat(messageService.filterBadWords(message));
    }


    @PostMapping("/chatroom/save-discussion")
    @ResponseBody
    public ResponseEntity<?> saveDiscussion(@Valid @RequestBody MessageRequest request) {
        messageService.saveDiscussion(request.getMessages());
        return new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Discussion Saved Successfully"), HttpStatus.OK);
    }

    @GetMapping("/chatroom/retrieve-discussion/{roomId}")
    @ResponseBody
    public ResponseEntity<?> retrieveDiscussions(@PathVariable Long roomId) {
        return new ResponseEntity<>(messageService.retrieveDiscussions(roomId), HttpStatus.OK);
    }

    @GetMapping("/chatrooms/most-active-rooms")
    @ResponseBody
    public ResponseEntity<?> mostActiveRooms() {
        return new ResponseEntity<>(chatRoomService.getMostActiveChatRooms(), HttpStatus.OK);
    }


}
