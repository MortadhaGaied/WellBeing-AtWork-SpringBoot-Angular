package com.wellbeignatwork.backend.service;


import com.google.firebase.messaging.*;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PushNotificationService {

    private final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private final FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendToTopic(PushNotificationRequest request){
        try{
            fcmService.sendMessageToTopic(request);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationToTopic(PushNotificationRequest request) throws FirebaseMessagingException {

        // These registration tokens come from the client FCM SDKs.
        List<String> registrationTokens = Arrays.asList(
                "YOUR_REGISTRATION_TOKEN_1",
                // ...
                "YOUR_REGISTRATION_TOKEN_n"
        );

    // Subscribe the devices corresponding to the registration tokens to the topic.
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                registrationTokens, request.getTopic());
    // See the TopicManagementResponse reference documentation
    // for the contents of response.
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
        // The topic name can be optionally prefixed with "/topics/".


// See documentation on defining a message payload.
        Message message = Message.builder()
                .setNotification(new Notification(request.getTitle(),request.getMessage()))
                .setTopic(request.getTopic())
                .build();

// Send a message to the devices subscribed to the provided topic.
        String response1 = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response1);
    }

}
