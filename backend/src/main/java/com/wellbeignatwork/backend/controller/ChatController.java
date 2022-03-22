package com.wellbeignatwork.backend.controller;


import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.payload.MessageRequest;
import com.wellbeignatwork.backend.payload.MessageResponse;
import com.wellbeignatwork.backend.payload.PushNotificationResponse;
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

    @PostMapping("/add-room")
    @ResponseBody
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @DeleteMapping("/delete-room")
    @ResponseBody
    public void deleteChatRoom(@RequestBody ChatRoom chatRoom) {
        chatRoomService.deleteChatRoom(chatRoom);
    }

    @PutMapping("/update-room")
    @ResponseBody
    public ChatRoom updateRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.updateChatRoom(chatRoom);
    }

    @GetMapping("/all-rooms")
    @ResponseBody
    @CrossOrigin("*")
    public List<ChatRoom> getAllRooms() {
        return chatRoomService.getAllRooms();
    }

    @GetMapping("/addUserToRoom/{chatRoomId}/{userId}")
    @ResponseBody
    public void addUserToChatRoom(@NotBlank @Valid @PathVariable Long chatRoomId,@NotBlank @Valid @PathVariable Long userId) {
        chatRoomService.addUserToChatRoom(chatRoomId, userId);
    }

    @GetMapping("/removeUserFromRoom/{chatRoomId}/{userId}")
    @ResponseBody
    public void removeUserFromRoom(@NotBlank @Valid @PathVariable Long chatRoomId,@NotBlank @Valid @PathVariable Long userId) {
        chatRoomService.removeUserFromChatRoom(chatRoomId, userId);
    }


    @MessageMapping("/chat/{roomId}/{senderID}")
    public void roomBasedChat(@Payload Message message, @DestinationVariable Long roomId, @DestinationVariable Long senderID) throws MessagingException {

        chatRoomService.roomBasedChat(messageService.filterBadWords(message), roomId, senderID);

    }

    @MessageMapping("/chat/public")
    public void publicCHat(@Payload Message message) {
        chatRoomService.publicChat(messageService.filterBadWords(message));
    }



    @PostMapping("/save-discussion")
    @ResponseBody
    public ResponseEntity<?> saveDiscussion(@Valid @RequestBody MessageRequest request){
        messageService.saveDiscussion(request.getMessages());
       return new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Discussion Saved Successfully"), HttpStatus.OK);
    }


}
