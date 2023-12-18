package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.repository.helpers.LoginHistoryEntry;
import com.hcmus.chatserver.repository.helpers.UserLoginTimeEntry;
import com.hcmus.chatserver.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loginhistory")
public class LoginHistoryController {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    LoginHistoryService loginHistoryService;

    @RequestMapping(value = "/logintime", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String getLoginTime() throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<Long> loginTime = loginHistoryService.getLoginTime();
            response.setData(loginTime);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get login history time");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String getUserLoginTime() throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            List<UserLoginTimeEntry> userLoginTime = loginHistoryService.getUserLoginTime();
            response.setData(userLoginTime);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user login history time");
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/{userId}/last", method = RequestMethod.GET)
    public @ResponseBody String getlastlogin(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            Long userLoginTime = loginHistoryService.getLastLoginOf(userId);
            response.setData(userLoginTime);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorReason("Can't get user login history time");
        }
        return mapper.writeValueAsString(response);
    }
}
