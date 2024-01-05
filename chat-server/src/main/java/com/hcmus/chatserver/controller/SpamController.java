package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.entities.spam.CreateSpamRequest;
import com.hcmus.chatserver.entities.spam.SpamReport;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.service.FriendService;
import com.hcmus.chatserver.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spam")
public class SpamController {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    SpamService spamService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String findAll() throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<SpamReport> reports = spamService.findAll();
            response.setData(reports);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get spam reports");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes ="application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String create(@RequestBody CreateSpamRequest request) throws Exception {
        System.out.println(mapper.writeValueAsString(request));
        ApiResponse response = new ApiResponse();
        try {
            spamService.insert(request.getUserIdSent(), request.getUserIdReported(), request.getContent());
            response.setData(true);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason(e.getMessage());
            e.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }
}
