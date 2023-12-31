package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.groupchat.ChatContentDTO;
import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.entities.groupchat.GroupChatMember;
import com.hcmus.chatserver.entities.messages.ClientChatMessage;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupChatService {
    private ChatSocketSessionContext context;
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

    public void addMember(int groupId, int userId) throws Exception {
        repository.addMember(groupId, userId);
        context.updateGroupChatMember(groupId);
    }

    public void removeMember(int groupId, int userId) throws Exception {
        int NoMem = 0;
        try {
            NoMem = repository.countMembers(groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (NoMem <= 2) {
            remove(groupId);
            return;
        }

        int adminId = repository.findGroupAdminById(groupId, userId);
        if (adminId == -1) {
            repository.removeMember(groupId, userId);
        } else {
            adminId = repository.findFirstNotAdmin(groupId);
            if (adminId == -1) {
                return;
            }

            repository.addAdmin(groupId, adminId);
            repository.removeMember(groupId, userId);
        }

        context.updateGroupChatMember(groupId);
    }

    public int create(String chatName, int admin, List<User> members) throws Exception {
        return repository.create(chatName, admin, members);
    }

    public int createEmptyGroup(String groupName) {
        return repository.createEmptyGroup(groupName);
    }

    public int remove(int chatId) throws Exception {
        int res = repository.remove(chatId);
        context.updateGroupChatMember(chatId);
        return res;
    }

    public int isAdminOf(int chatId, int userId) {
        return repository.isAdminOf(chatId, userId);
    }

    public int updateMemberRole(int groupId, int userId, int role) {
        return repository.updateMemberRole(groupId, userId, role);
    }

    public int countMembers(int groupId) throws Exception {
        return repository.countMembers(groupId);
    }

    // if group chat is not a group, return the other member id
    // else return -1
    public int getOtherMemberId(int groupId, int userId) throws Exception {
        if (repository.isGroup(groupId)) return -1;
        return repository.getOtherMemberId(groupId, userId);
    }

    public void setContext(ChatSocketSessionContext context) {
        this.context = context;
    }
}
