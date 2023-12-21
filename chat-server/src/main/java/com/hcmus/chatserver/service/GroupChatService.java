package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.groupchat.ChatContentDTO;
import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.entities.groupchat.GroupChatMember;
import com.hcmus.chatserver.entities.messages.ClientChatMessage;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.GroupChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GroupChatService {

    private final GroupChatRepository repository;

    public GroupChatService(GroupChatRepository repository) {
        this.repository = repository;
    }

    public List<GroupChat> findGroupChatOfUser(int userId) {
        return repository.findByMemberId(userId);
    }

    public List<GroupChat> findAllGroupChat() throws Exception {
        return repository.findAll();
    }

    public List<User> findAllMembers(int groupId) throws Exception {
        return repository.findAllMembers(groupId);
    }

    public List<User> findAllAdmins(int groupId) throws Exception {
        return repository.findAllAdmins(groupId);
    }

    public List<Integer> findAllGroupChatByUserId(int userId) throws Exception {
        List<GroupChat> gchat = repository.findByMemberId(userId);
        List<Integer> gchatId = new ArrayList<>(gchat.size());
        for (GroupChat chat : gchat) {
            gchatId.add(chat.getGroupId());
        }
        return gchatId;
    }

    public List<GroupChatMember> findAllMembersOfGroup(int gchatId) throws Exception {
        return repository.findMembersOf(gchatId);
    }

    public long countNoGroupChatOf(int userId) throws Exception {
        return repository.countNoGroupChatOf(userId);
    }

    public void persistMsg(ClientChatMessage msg) throws Exception {
        repository.persistChatMsg(msg);
    }

    public List<ChatContentDTO> getAllMsg(int gChatId) throws Exception {
        return repository.getAllMsg(gChatId);
    }

    public void updateGroupChatName(int gchatid, String newName) throws Exception {
        repository.updateGroupChatName(gchatid, newName);
    }
}
