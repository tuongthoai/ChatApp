package com.hcmus.ui.datatest;

public class User {
    private String userId;
    private String username;
    private String fullname;

    public User(String userId, String username, String fullname) {
        this.userId = userId;
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
