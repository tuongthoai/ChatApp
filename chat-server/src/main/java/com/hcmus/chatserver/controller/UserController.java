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
    public String addUser(@RequestBody User user) throws Exception {
//        System.out.println("UserController: " + user.getBirthday());
        ApiResponse response = new ApiResponse();
        int user_id = -1;
        try {
            user_id = userService.addUser(user);
            response.setData(user_id);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't add user");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody String removeUser(@RequestBody int userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            userService.removeUser(userId);
            response.setData("Remove successfully");
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't remove user");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String updateUser(@RequestBody User updatedUser) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            userService.updateUser(updatedUser);
            response.setData("Update successfully");
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't update user");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/adminBlock/{userId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String adminBlockUser(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            userService.adminBlockUser(userId);
            response.setData("Block successfully");
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't block user");
        }
        return mapper.writeValueAsString(response);
    }

}
