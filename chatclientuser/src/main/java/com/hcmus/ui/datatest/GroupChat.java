package com.hcmus.ui.datatest;

public class GroupChat {
    private String groupId;
    private String groupName;

    public GroupChat(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
