package com.hcmus.chatserver.entities.messages;

public class ClientChatMessage {
    private int userSentId;
    private int groupChatId;
    private String username;
    private String msgType;
    private String msgContent;

    public ClientChatMessage() {
    }

    public int getUserSentId() {
        return userSentId;
    }

    public void setUserSentId(int userSentId) {
        this.userSentId = userSentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
    }
}
