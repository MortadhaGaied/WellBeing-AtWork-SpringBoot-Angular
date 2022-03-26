package com.wellbeignatwork.backend.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;

import java.util.List;

public interface INotificationService {

     void sendPushNotificationToToken(PushNotificationRequest request);
     void sendToTopic(PushNotificationRequest request);
     void subScribeUsersToTopic(List<String> tokens, String topic) throws FirebaseMessagingException;
     void sendPushNotificationToALlUsers(String message,String title) throws FirebaseMessagingException;
     void chatReminderForInavtiveUsers() throws FirebaseMessagingException;
}
