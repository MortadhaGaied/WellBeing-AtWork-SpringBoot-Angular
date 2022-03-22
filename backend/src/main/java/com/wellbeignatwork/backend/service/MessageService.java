package com.wellbeignatwork.backend.service;


import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.repository.MessageRepository;
import com.wellbeignatwork.backend.utils.BadWordFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //filter all bad words coming from messaged and replace them with *** (profanity)
    public Message filterBadWords(Message message) {
        String filteredContent =  BadWordFilter.getCensoredText(message.getContent());
        message.setContent(filteredContent);
        return message;
    }


    public void saveDiscussion(List<Message> messages) {
        messages.forEach(messageRepository::save);

    }

    // delete all Messaged evry week At 00:00 on Sunday.
    @Scheduled(cron = "0 0 0 * * 0")
    public void clearMessages(){
        messageRepository.deleteAll();
        log.info("all Messaged are Cleaned from database");
    }

}
