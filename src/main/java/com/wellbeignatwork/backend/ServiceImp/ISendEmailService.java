package com.wellbeignatwork.backend.ServiceImp;

import javax.mail.MessagingException;

public interface ISendEmailService {
    void sendSimpleEmail( String toEmail,
                          String body,
                          String subject);
    void sendMailWithAttachement( String toEmail,
                          String body,
                          String subject,
                          String attachement) throws MessagingException;
}
