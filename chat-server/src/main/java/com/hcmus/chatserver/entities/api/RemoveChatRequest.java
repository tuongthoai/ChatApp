package com.hcmus.chatserver.entities.api;

public class RemoveChatRequest {
    private int chatId;

    public RemoveChatRequest() {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
