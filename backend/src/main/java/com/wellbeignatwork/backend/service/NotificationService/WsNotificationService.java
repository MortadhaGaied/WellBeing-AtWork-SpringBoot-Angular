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
    private Set<String> listeners = new HashSet<>();

    @Autowired
    public WsNotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }


    public void add(String sessionId) {
        listeners.add(sessionId);
    }

    public void remove(String sessionId) {
        listeners.remove(sessionId);
    }

    public void dispatch(Notification notification,Long userId) {
        for (String listener : listeners) {
            log.info("Sending notification to " + listener);

            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(listener);

            headerAccessor.setLeaveMutable(true);

            int value = (int) Math.round(Math.random() * 100d);
            log.info("Sending notification to " + listener);
            template.convertAndSendToUser(
                    listener,
                    "/topic2/item/"+userId,
                    notification,
                    headerAccessor.getMessageHeaders());
        }
    }

    @EventListener
    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("Disconnecting " + sessionId + "!");
        remove(sessionId);
    }
}