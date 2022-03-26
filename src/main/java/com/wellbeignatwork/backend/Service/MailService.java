package com.wellbeignatwork.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
    public class MailService {



        private JavaMailSender emailSender;

        @Value("${spring.mail.username}")
        private String from;

        public void sendMail(String to, String subject, String content, Boolean html) {
            try {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setText(content, html);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setFrom(from, "Well Been");
                emailSender.send(mimeMessage);
            } catch(Exception e) {
                e.printStackTrace();
            }

        }

    }

