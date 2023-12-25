package com.hcmus.models;

import java.util.List;

public class CreateChatRequest {
    private String chatName;
    private int adminId;
    private List<User> members;

    public CreateChatRequest() {
    }

    public CreateChatRequest(String chatName, int adminId, List<User> members) {
        this.chatName = chatName;
        this.adminId = adminId;
        this.members = members;
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
