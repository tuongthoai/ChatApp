package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.entities.user.User;
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

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String finalAllFriend(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<User> friends = friendService.findAll(userId);
            response.setData(friends);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/{userId}/online", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String finalAllFriendOnline(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<User> friends = friendService.findAllFriendOnline(userId);
            response.setData(friends);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends online");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/{userId}/count", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String countFriends(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            Long no_friends = friendService.countFriendsOf(userId);
            response.setData(no_friends);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends online");
        }
        return mapper.writeValueAsString(response);
    }
}
