package com.hcmus.chatserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.api.*;
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

    @PostMapping(value = "/addMember", consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String addMember(@RequestBody AddMemberRequest request) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            service.addMember(request.getGroupId(), request.getNewMemberId());
            response.setData(true);
        } catch (Exception ex) {
            response.setError(true);
            response.setErrorReason(ex.getMessage());
            ex.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }

    @PostMapping(value = "/removeMember", consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String removeMember(@RequestBody AddMemberRequest request) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            service.removeMember(request.getGroupId(), request.getNewMemberId());
            response.setData(true);
        } catch (Exception ex) {
            response.setError(true);
            response.setErrorReason(ex.getMessage());
            ex.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }

    @PostMapping(value = "create", consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String create(@RequestBody CreateChatRequest request) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
//            service.removeMember(request.getGroupId(), request.getNewMemberId());
            int result = service.create(request.getChatName(), request.getAdminId(), request.getMembers());
            response.setData(result);
        } catch (Exception ex) {
            response.setError(true);
            response.setErrorReason(ex.getMessage());
            ex.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }

    @DeleteMapping(value = "/remove", consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String remove(@RequestBody RemoveChatRequest request) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            int result = service.remove(request.getChatId());
            response.setData(result);
        } catch (Exception ex) {
            response.setError(true);
            response.setErrorReason(ex.getMessage());
            ex.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }

    @PostMapping(value = "/checkingAdmin", consumes = "application/json", produces = "application/json; charset=utf-8")
    public @ResponseBody String isAdminOf(@RequestBody CheckingAdminRequest request) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            int result = service.isAdminOf(request.getChatId(), request.getUserId());
            response.setData(result);
        } catch (Exception ex) {
            response.setError(true);
            response.setErrorReason(ex.getMessage());
            ex.printStackTrace();
        }
        return mapper.writeValueAsString(response);
    }
}
