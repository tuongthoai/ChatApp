package com.hcmus.chatserver.repository.helpers;

public class UserLoginTimeEntry {
    private long loginTime;
    private String username;
    private String fullname;

    public UserLoginTimeEntry() {
    }

    public UserLoginTimeEntry(long loginTime, String username, String fullname) {
        this.loginTime = loginTime;
        this.username = username;
        this.fullname = fullname;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
