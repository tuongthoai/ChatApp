package com.hcmus.ui.datatest;

public class GroupChatMember {
    private String groupId;
    private String memberId;

    public GroupChatMember(String groupId, String memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
