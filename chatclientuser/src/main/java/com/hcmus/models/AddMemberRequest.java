package com.hcmus.models;

public class AddMemberRequest {
    private int groupId;
    private int newMemberId;

    public AddMemberRequest() {
    }

    public AddMemberRequest(int groupId, int newMemberId) {
        this.groupId = groupId;
        this.newMemberId = newMemberId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getNewMemberId() {
        return newMemberId;
    }

    public void setNewMemberId(int newMemberId) {
        this.newMemberId = newMemberId;
    }
}
