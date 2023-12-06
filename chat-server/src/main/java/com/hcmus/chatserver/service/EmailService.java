package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
    @Autowired private JavaMailSender javaMailSender;
    String sendSimpleEmail(EmailDetails emailDetails){
        return "Email sent successfully";
    }
    String sendEmailWithAttachment(EmailDetails emailDetails){
        return "Email sent successfully";
    }
}
