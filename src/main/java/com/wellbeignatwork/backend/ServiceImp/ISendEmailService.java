package com.wellbeignatwork.backend.ServiceImp;

public interface ISendEmailService {
    void sendSimpleEmail( String toEmail,
                          String body,
                          String subject);
}
