package com.wellbeignatwork.backend.service;


import com.google.firebase.messaging.*;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import com.wellbeignatwork.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PushNotificationService {

    private final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private final FCMService fcmService;
    private final UserRepository userRepository;

    @Autowired
    public PushNotificationService(FCMService fcmService, UserRepository userRepository) {
        this.fcmService = fcmService;
        this.userRepository = userRepository;
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendToTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToTopic(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void subScribeUsersToTopic(List<String> tokens, String topic) throws FirebaseMessagingException {

        // Subscribe the devices corresponding to the registration tokens to the topic.
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                tokens, topic);
        // See the TopicManagementResponse reference documentation
        // for the contents of response.
        log.info(response.getSuccessCount() + " tokens were subscribed successfully");
        // The topic name can be optionally prefixed with "/topics/".


    }


    public void sendPushNotificationToALlUsers(String message,String title) throws FirebaseMessagingException {
        List<String> tokens = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getFireBaseToken() != null) {
                tokens.add(user.getFireBaseToken());
            }
        });

        subScribeUsersToTopic(tokens, "all-users");
        sendToTopic(new PushNotificationRequest(title, message, "all-users"));
    }

}
