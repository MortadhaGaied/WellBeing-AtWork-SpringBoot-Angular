package com.wellbeignatwork.backend.controller.Chat;

import com.wellbeignatwork.backend.entity.Chat.Notification;
import com.wellbeignatwork.backend.entity.Chat.NotificationType;
import com.wellbeignatwork.backend.service.NotificationService.WsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WsNotificationController {
    private final WsNotificationService dispatcher;
    @Autowired

    public WsNotificationController(WsNotificationService dispatcher){
        this.dispatcher=dispatcher;
    }

    @MessageMapping("/start/{userID}")
    public void start(@DestinationVariable("userID") Long userID, StompHeaderAccessor stompHeaderAccessor) {
        dispatcher.add(stompHeaderAccessor.getSessionId());
        Notification notification = new Notification();
        notification.setBody("test to user "+userID);
        notification.setType(NotificationType.MESSAGE);
        //notification.setData("{'test':'true'}");
        //dispatcher.dispatch(notification,userID);
    }

    @MessageMapping("/stop")
    public void stop(StompHeaderAccessor stompHeaderAccessor) {
        dispatcher.remove(stompHeaderAccessor.getSessionId());
    }
}
