package com.wellbeignatwork.backend.payload;

import com.wellbeignatwork.backend.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {
    int status;
    String message;
    ChatRoom chatRoom;
    List<ChatRoom> chatRoomList;
}
