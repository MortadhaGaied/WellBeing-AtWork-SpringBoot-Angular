package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.ServiceImp.ISendEmailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

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
    public void sendMailWithAttachement(String toEmail, String body, String subject,String attchment) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("willbieng7@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource= new FileSystemResource(new File(attchment));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        mailSender.send(mimeMessage);

        System.out.println("Mail send sucessufully");
    }
}
