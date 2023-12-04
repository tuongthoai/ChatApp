package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hcmus.chatserver.entities.user.User;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String findAll() throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<User> users = userService.findAll();
            response.setData(users);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get users");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String addUser(@RequestBody User user) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            userService.addUser(user);
            response.setData(user);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't add user");
        }
        return mapper.writeValueAsString(response);
    }
}
