package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.response.RequestResponse;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.FriendRepository;
import com.hcmus.chatserver.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    FriendService friendService;

    @GetMapping("/{userId}")
    public @ResponseBody String finalAllFriend(@PathVariable Integer userId) throws Exception {
        RequestResponse response = new RequestResponse();
        try {
            List<User> friends = friendService.findAll(userId);
            response.setData(friends);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends");
        }
        return mapper.writeValueAsString(response);
    }

    @GetMapping("/{userId}/online")
    public @ResponseBody String finalAllFriendOnline(@PathVariable Integer userId) throws Exception {
        RequestResponse response = new RequestResponse();
        try {
            List<User> friends = friendService.findAllFriendOnline(userId);
            response.setData(friends);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends online");
        }
        return mapper.writeValueAsString(response);
    }
}
