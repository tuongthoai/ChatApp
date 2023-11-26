package com.hcmus.ui.datatest;

public class GroupChatContent {
    private String groudId;
    private String userSentId;
    private String msg;

    public GroupChatContent(String groudId, String userSentId, String msg) {
        this.groudId = groudId;
        this.userSentId = userSentId;
        this.msg = msg;
    }

    public String getGroudId() {
        return groudId;
    }

    public void setGroudId(String groudId) {
        this.groudId = groudId;
    }

    public String getUserSentId() {
        return userSentId;
    }

    public void setUserSentId(String userSentId) {
        this.userSentId = userSentId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
