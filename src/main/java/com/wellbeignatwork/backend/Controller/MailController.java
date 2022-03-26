package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
