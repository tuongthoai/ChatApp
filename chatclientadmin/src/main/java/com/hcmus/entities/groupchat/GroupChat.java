package com.hcmus.entities.groupchat;

import java.util.Arrays;
import java.util.List;

public class GroupChat {
    private int groupId;
    private String groupName;
    private long createdTime;
    private boolean group;

    public GroupChat() {
    }

    public static List<String> getColumnNames() {
        return Arrays.asList(
                "Group Id", "Group Name", "Created Time", "Group"
        );
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }
}
