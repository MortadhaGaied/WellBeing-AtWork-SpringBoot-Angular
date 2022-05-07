package com.wellbeignatwork.backend.service.NotificationService;


import com.wellbeignatwork.backend.entity.Chat.Notification;
import com.wellbeignatwork.backend.entity.Chat.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Slf4j
@Service
public class WsNotificationService {
    private final SimpMessagingTemplate template;


    @Autowired
    public WsNotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }




    public void dispatch(Notification notification,Long userId) {
        template.convertAndSend(

                "/topic2/item/"+userId,
                notification
        );

    }
    /*
  @Scheduled(fixedRate = 3000)
    public void dispatch2() {
        Notification notification = new Notification();
        notification.setBody("azeazeaze");
        notification.setTitle("azeazeazeaze");
        template.convertAndSend(

                "/topic2/item/"+"2",
                notification
        );

    }*/


}