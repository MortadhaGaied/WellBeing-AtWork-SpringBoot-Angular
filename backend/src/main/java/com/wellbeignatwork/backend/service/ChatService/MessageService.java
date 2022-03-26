package com.wellbeignatwork.backend.service.ChatService;


import com.wellbeignatwork.backend.entity.Message;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.exceptions.chatExceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.repository.ChatRoomRepository;
import com.wellbeignatwork.backend.repository.MessageRepository;
import com.wellbeignatwork.backend.repository.UserRepository;
import com.wellbeignatwork.backend.util.BadWordFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Slf4j
@Service
public class MessageService implements IMessageService{
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    //filter all bad words coming from messaged and replace them with *** (profanity)
    public Message filterBadWords(Message message) {
        String filteredContent = BadWordFilter.getCensoredText(message.getContent());
        message.setContent(filteredContent);
        return message;
    }


    public void saveDiscussion(List<Message> messages) {
        messages.forEach(messageRepository::save);

    }


    public List<Message> retrieveDiscussions(@NotBlank Long roomId) {
        return chatRoomRepository.findById(roomId).map((messageRepository::getMessagesByChatroom))
                .orElseThrow(() -> new ResourceNotFoundException("chat room with id : " + roomId + "cannot be found verify your entry"));

    }

    
    @Scheduled(fixedRate = 30000)
    public void getTopChattersGlobally() {
        Map<User, Integer> chatters = new HashMap<>();
        Map<User, Integer> chattesMap = new HashMap<>();
        List<User> topchatter = new ArrayList<>();
        if(!messageRepository.findAll().isEmpty()){
            messageRepository
                    .findAll()
                    .forEach(message -> {
                        chattesMap.put(message.getSender(), 0);
                        //chattesMap.keySet().add(message.getSender());
                        topchatter.add(message.getSender());
                    });


            for (User user : chattesMap.keySet()) {
                for (User value : topchatter) {
                    if (user.equals(value)) {
                        chattesMap.put(user, chattesMap.get(user) + 1);

                    }
                }
            }

            Map.Entry<User, Integer> maxEntry = null;
            int a = 0;
            while (a <= 2) {

                for (Map.Entry<User, Integer> entry : chattesMap.entrySet()) {
                    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                        maxEntry = entry;
                    }
                }
                chatters.put(maxEntry.getKey(), maxEntry.getValue());
                chattesMap.put(maxEntry.getKey(), 0);
                a++;
            }

            for (User user : chatters.keySet()) {
                log.info("user : + " + user + "has : " + chatters.get(user) + " messages");
            }

        }


    }


    // delete all Messaged evry week At 00:00 on Sunday.
    //@Scheduled(cron = "0 0 0 * * 0")
    @Scheduled(cron = "0 0 1 * * MON")
    public void clearMessages() {
        messageRepository.deleteAllInBatch();
        log.info("all Messages are Cleaned from database");
    }

}
