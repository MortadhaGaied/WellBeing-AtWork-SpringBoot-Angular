package com.wellbeignatwork.backend.payload;

import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @NotBlank
    List<Message>messages;
}
