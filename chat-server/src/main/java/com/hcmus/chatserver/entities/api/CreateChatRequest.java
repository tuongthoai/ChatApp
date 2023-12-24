package com.hcmus.chatserver.entities.api;

import com.hcmus.chatserver.entities.user.User;

import java.util.List;

public class CreateChatRequest {
    private String chatName;
    private int adminId;
    private List<User> members;

    public CreateChatRequest() {
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
