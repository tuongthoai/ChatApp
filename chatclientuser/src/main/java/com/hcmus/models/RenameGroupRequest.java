package com.hcmus.models;

public class RenameGroupRequest {
    private int groupId;
    private String newName;

    public RenameGroupRequest() {
    }

    public RenameGroupRequest(int groupId, String newName) {
        this.groupId = groupId;
        this.newName = newName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
