package com.hcmus.chatserver.entities.user;

public class UserStatisticSummary {
    private long lastLogin;
    private long NoFiends;
    private long NoGroupChat;

    public UserStatisticSummary() {
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getNoFiends() {
        return NoFiends;
    }

    public void setNoFiends(long noFiends) {
        NoFiends = noFiends;
    }

    public long getNoGroupChat() {
        return NoGroupChat;
    }

    public void setNoGroupChat(long noGroupChat) {
        NoGroupChat = noGroupChat;
    }
}
