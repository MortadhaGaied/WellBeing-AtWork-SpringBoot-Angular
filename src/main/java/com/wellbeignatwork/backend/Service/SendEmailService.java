package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.ServiceImp.ISendEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService implements ISendEmailService {
    private JavaMailSender mailSender;

    public SendEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail( String toEmail,
                                String body,
                                String subject) {
        System.out.println("sending Email");
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("wellbeingatwork66@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Sent successfully......");
    }
}
