package com.hcmus.ui.datatest;

public class GroupChatAdmin {
    private String groupId;
    private String adminId;

    public GroupChatAdmin(String groupId, String adminId) {
        this.groupId = groupId;
        this.adminId = adminId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
