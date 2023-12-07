package com.hcmus.chatserver.entities.api;

public class LogginRequest {
    private String username;
    private String password;

    public LogginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
