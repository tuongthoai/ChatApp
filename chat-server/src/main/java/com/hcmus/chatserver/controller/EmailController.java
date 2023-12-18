package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.entities.email.EmailDetails;
import com.hcmus.chatserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String sendEmail(@RequestBody EmailDetails emailDetails) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            String status = emailService.sendSimpleEmail(emailDetails);
            response.setData(status);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't send email");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/sendMailWithAttachment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String sendEmailWithAttachment(@RequestBody EmailDetails emailDetails) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            String status = emailService.sendEmailWithAttachment(emailDetails);
            response.setData(status);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't send email");
        }
        return mapper.writeValueAsString(response);
    }
}
