package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.entities.user.UserDTO;
import com.hcmus.chatserver.repository.helpers.UserActivityEntry;
import com.hcmus.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hcmus.chatserver.entities.user.User;

import javax.websocket.server.PathParam;
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
        System.out.println("UserController: findAll");
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

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody String getUserById(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            User user = userService.getUser(userId);
            response.setData(user);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user");
        }
        return mapper.writeValueAsString(response);
    }
    @RequestMapping(value = "/createdTime", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String getCreatedTime() throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<Long> createdTime = userService.getCreatedTime();
            response.setData(createdTime);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get created time");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value= "/newUser", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8", params = {"startDate", "endDate"})
    public @ResponseBody String getNewUser(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            // check date format
            System.out.println(startDate);
            if (startDate.length() != 10 || endDate.length() != 10) {
                throw new Exception("Date format is not correct");
            }

            List<User> users = userService.getNewUser(startDate, endDate);
            response.setData(users);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get new user");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value= "/activity", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8", params = {"startDate", "endDate"})
    public @ResponseBody String getUserActivity(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            // check date format
            System.out.println(startDate);
            if (startDate.length() != 10 || endDate.length() != 10) {
                throw new Exception("Date format is not correct");
            }

            List<UserActivityEntry> users = userService.getUserActivity(startDate, endDate);
            response.setData(users);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user activity");
        }
        return mapper.writeValueAsString(response);
    }
    @RequestMapping(value = "/dirInDirFriend", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String getDirInDirFriend() throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<UserDTO> dirInDirFriend = userService.getDirInDirFriend();
            response.setData(dirInDirFriend);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get direct - indirect friend");
        }
        return mapper.writeValueAsString(response);
    }
}
