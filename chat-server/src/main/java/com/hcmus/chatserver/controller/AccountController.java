package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.entities.api.LogginRequest;
import com.hcmus.chatserver.entities.api.RegisterRequest;
import com.hcmus.chatserver.service.AccountService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController implements InitializingBean {
    @Autowired
    private AccountService service;
    private ObjectMapper mapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String loggin(@RequestBody LogginRequest request) throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        int userId = -1;
        try {
            userId = service.authenticateUser(request.getUsername(), request.getPassword());
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Error! Please try a gain!!!");
        } finally {
            response.setData(userId);
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public String registerUser(@RequestBody RegisterRequest request) throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        int userId = -1;
        try {
            userId = service.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
            response.setData(userId);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Error! Please try a gain!!!");

            return mapper.writeValueAsString(response);
        }
        return mapper.writeValueAsString(response);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }
}