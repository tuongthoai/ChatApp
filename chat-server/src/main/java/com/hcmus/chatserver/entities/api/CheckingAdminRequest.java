package com.hcmus.chatserver.entities.api;

public class CheckingAdminRequest {
    private int chatId;
    private int userId;

    public CheckingAdminRequest() {
    }

    public CheckingAdminRequest(int chatId, int userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
