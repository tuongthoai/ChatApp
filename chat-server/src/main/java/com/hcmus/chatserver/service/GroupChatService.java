package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.repository.GroupChatRepository;
import org.springframework.stereotype.Service;

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
}
