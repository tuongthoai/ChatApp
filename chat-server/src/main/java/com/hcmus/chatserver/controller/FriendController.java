package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @GetMapping(value = "/{userId}/exceptGroup/{groupId}")
    public @ResponseBody String getFriendNotInGroup(@PathVariable int userId, @PathVariable int groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<User> users = friendService.getFriendNotInGroup(userId, groupId);
            response.setData(users);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends");
            e.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }

    @GetMapping(value = "/{userId}/stranger")
    public @ResponseBody String getStranger(@PathVariable int userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<User> users = friendService.findAllStranger(userId);
            response.setData(users);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user's friends");
            e.printStackTrace(System.err);
        }
        return mapper.writeValueAsString(response);
    }

    @DeleteMapping(value = "/{userId}/unfriend/{friendId}")
    public @ResponseBody String removeFriend(@PathVariable int userId, @PathVariable int friendId) throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        try {
            friendService.removeFriend(userId, friendId);
            response.setData("Friend removed successfully");
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Unable to remove friend");
            e.printStackTrace(System.err);
        }
        return mapper.writeValueAsString(response);
    }

    @PostMapping(value = "/{userId}/addfriend/{friendId}")
    public @ResponseBody String addFriend(@PathVariable int userId, @PathVariable int friendId) throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        try {
            friendService.addFriend(userId, friendId);
            response.setData("Friend added successfully");
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Unable to add friend");
            e.printStackTrace(System.err);
        }
        return mapper.writeValueAsString(response);
    }
}
