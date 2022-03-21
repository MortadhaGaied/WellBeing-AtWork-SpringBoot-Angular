package com.wellbeignatwork.backend.service;


import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }
    public Message filterBadWords(Message message){
        if (message.getContent().contains("amine")) {
            //filtering logic goes here ..

            message.setContent(message.getContent().replace("amine","****"));

        }
        return message;
    }


    public void saveDiscussion(List<Message>messages){
        messages.forEach(messageRepository::save);

    }

}
