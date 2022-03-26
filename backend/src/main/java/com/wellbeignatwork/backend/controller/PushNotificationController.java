package com.wellbeignatwork.backend.controller;



import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import com.wellbeignatwork.backend.payload.PushNotificationResponse;
import com.wellbeignatwork.backend.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class PushNotificationController {


    private final PushNotificationService pushNotificationService;


    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }
    //http://localhost:8089/api/v1/notification/token
    @PostMapping("/token")
    public ResponseEntity<?> sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    //http://localhost:8089/api/v1/notification/topic
    @PostMapping("/topic")
    public ResponseEntity<?> sendTopicNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendToTopic(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    //http://localhost:8089/api/v1/notification/notifyAll
    @PostMapping("/notifyAll")
    public ResponseEntity<?> sendGlobalNotification(@RequestBody PushNotificationRequest request) throws FirebaseMessagingException {
        pushNotificationService.sendPushNotificationToALlUsers(request.getTitle(),request.getMessage());
     return  new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent to all users."), HttpStatus.OK);
    }

    @PostMapping("/token/{userId}/{t}")
    public void saveUserFirebaseTokens(@PathVariable Long userId, @PathVariable String t){
        pushNotificationService.saveFirebaseToken(userId,t);
    }

}
