package com.wellbeignatwork.backend.entity.Chat;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Map;

@Data
public class Notification {
    public String title;
    public String body;
    public NotificationType type;
    public Map<String, String> data;
    public DateTime sentAt;
    public Boolean opened=false;

}
