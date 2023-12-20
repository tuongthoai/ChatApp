package com.hcmus.chatserver.entities.user;

import lombok.Data;

@Data
public class UserActivity {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String sex;
    private String address;
    private long birthday;
    private long createdTime;
    private boolean online = false;
    private boolean blocked = false;
    private int loginCount;
    private int chatWithCount;
    private int chatGroupCount;

    public UserActivity() {
    }
}
