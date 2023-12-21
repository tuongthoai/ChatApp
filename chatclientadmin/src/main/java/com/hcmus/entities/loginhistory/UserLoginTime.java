package com.hcmus.entities.loginhistory;

import java.util.List;

public class UserLoginTime {
    private long loginTime;
    private String username;
    private String fullname;

    public UserLoginTime() {}
    public UserLoginTime(long loginTime, String username, String fullname) {
        this.loginTime = loginTime;
        this.username = username;
        this.fullname = fullname;
    }

    public static List<String> getColumnNames() {
        return List.of("Login Time", "Username", "Fullname");
    }

    public long getLoginTime() {
        return loginTime;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }
}
