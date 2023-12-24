package com.hcmus.models;

public class UpdateMemberRoleRequest {
    private int groupId;
    private int userId;
    private int role; // 1 for admin and 2 for user

    public UpdateMemberRoleRequest() {
    }

    public UpdateMemberRoleRequest(int groupId, int userId, int role) {
        this.groupId = groupId;
        this.userId = userId;
        this.role = role;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
