package com.wellbeignatwork.backend.controller;

import com.wellbeignatwork.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController

public class MailController {
    @Autowired
    MailService mailService;

    @GetMapping("/mail")

    public void sendmail() throws MessagingException, UnsupportedEncodingException {
        mailService.sendMail("saidbouchouicha8@gmail.com", "Welcome", "hello", false);

    }
}
