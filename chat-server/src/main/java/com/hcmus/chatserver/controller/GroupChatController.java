package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.ApiResponse;
import com.hcmus.chatserver.entities.api.RenameGroupRequest;
import com.hcmus.chatserver.service.GroupChatService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gchats")
public class GroupChatController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final GroupChatService service;

    public GroupChatController(GroupChatService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody String findGchats(@PathVariable Integer userId) throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        response.setData(service.findGroupChatOfUser(userId));
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody String findAllGChats() throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllGroupChat());
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/members/{groupId}", method = RequestMethod.GET)
    public @ResponseBody String findAllMembers(@PathVariable Integer groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllMembers(groupId));
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/admins/{groupId}", method = RequestMethod.GET)
    public @ResponseBody String findAllAdmins(@PathVariable Integer groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllAdmins(groupId));
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/members/show/{groupId}", method = RequestMethod.GET)
    public @ResponseBody String findAllMembersOfGroup(@PathVariable Integer groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.findAllMembersOfGroup(groupId));
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/{userId}/count", method = RequestMethod.GET)
    public @ResponseBody String countGroupChatOf(@PathVariable Integer userId) throws Exception {
        ApiResponse response = new ApiResponse();
        response.setData(service.countNoGroupChatOf(userId));
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/{groupId}/messages", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String getGroupChatContent(@PathVariable Integer groupId) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            response.setData(service.getAllMsg(groupId));
        } catch (Exception err) {
            response.setError(true);
            response.setErrorReason(err.getMessage());
            err.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String updateGChatName(@RequestBody RenameGroupRequest request) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            service.updateGroupChatName(request.getGroupId(), request.getNewName());
            response.setData(true);
        } catch (Exception err) {
            response.setError(true);
            response.setErrorReason(err.getMessage());
            err.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }
}
