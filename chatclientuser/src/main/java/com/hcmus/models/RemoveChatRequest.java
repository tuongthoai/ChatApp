package com.hcmus.models;

public class RemoveChatRequest {
    private int chatId;

    public RemoveChatRequest() {
    }

    public RemoveChatRequest(int chatId) {
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
