package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.service.GroupChatService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupChatController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final GroupChatService service;

    public GroupChatController(GroupChatService service) {
        this.service = service;
    }

    @RequestMapping(value = "/gchats/{userId}", method = RequestMethod.GET)
    public @ResponseBody String findGchats(@PathVariable Integer userId) throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        response.setData(service.findGroupChatOfUser(userId));
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/gchats/all", method = RequestMethod.GET)
    public @ResponseBody String findAllGChats() throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllGroupChat());
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/gchats/members/{groupId}", method = RequestMethod.GET)
    public @ResponseBody String findAllMembers(@PathVariable Integer groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllMembers(groupId));
        return mapper.writeValueAsString(response);
    }
    @RequestMapping(value = "/gchats/admins/{groupId}", method = RequestMethod.GET)
    public @ResponseBody String findAllAdmins(@PathVariable Integer groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllAdmins(groupId));
        return mapper.writeValueAsString(response);
    }
}
