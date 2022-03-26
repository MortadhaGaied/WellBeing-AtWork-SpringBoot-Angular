package com.wellbeignatwork.backend.service.ChatService;

import com.wellbeignatwork.backend.entity.Message;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface IMessageService {
    public Message filterBadWords(Message message);
    public void saveDiscussion(List<Message> messages);
    public List<Message> retrieveDiscussions(@NotBlank Long roomId);
    public void getTopChattersGlobally();
    public void clearMessages();
}
