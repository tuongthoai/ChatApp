package com.hcmus.ui.datatest;

public class GroupChatOfUser {
    private String userId;
    private String groupId;

    public GroupChatOfUser(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
