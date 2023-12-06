package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Objects;

@Service
public class EmailService {
    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;
    public String sendSimpleEmail(EmailDetails emailDetails){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(emailDetails.getRecipient());
            message.setSubject(emailDetails.getSubject());
            message.setText(emailDetails.getMsgBody());

            javaMailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return "Email sent failed";
        }
    }
    public String sendEmailWithAttachment(EmailDetails emailDetails){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(sender);
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());
            helper.setText(emailDetails.getMsgBody());
            FileSystemResource file = new FileSystemResource(emailDetails.getAttachment());
            helper.addAttachment(Objects.requireNonNull(file.getFilename()),file);
            javaMailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return "Email sent failed";
        }
    }
}
