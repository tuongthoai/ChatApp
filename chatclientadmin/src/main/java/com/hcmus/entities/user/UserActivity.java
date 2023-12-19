package com.hcmus.entities.user;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

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

    public static List<String> getColumnNames() {
        return Arrays.asList(
                "ID", "Username", "Password", "Name", "Email",
                "Sex", "Address", "Birthday", "Created Time", "Online", "Blocked", "Login Count", "Chat With Count", "Chat Group Count"
        );
    }
}
