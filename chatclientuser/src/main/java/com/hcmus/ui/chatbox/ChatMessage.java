package com.hcmus.ui.chatbox;

public class ChatMessage {
    private String username;
    private String avatar;
    private String message;

    public ChatMessage(String username, String avatar, String message) {
        this.username = username;
        this.avatar = avatar;
        this.message = message;
    }

    public String toString() {
        return "ChatMessage{" +
                "username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getMessage() {
        return message;
    }
}
