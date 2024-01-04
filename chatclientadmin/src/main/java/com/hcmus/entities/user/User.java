package com.hcmus.entities.user;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class User {
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
    private String role;

    public User() {
    }

    public User(int id, String username, String password, String name, String email, String sex, String address, long birthday, long createdTime, boolean online, boolean blocked){
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.address = address;
        this.birthday = birthday;
        this.createdTime = createdTime;
        this.online = online;
        this.blocked = blocked;
    }

    public static List<String> getColumnNames() {
        return Arrays.asList(
                "ID", "Username", "Password", "Name", "Email",
                "Sex", "Address", "Birthday", "Created Time", "Online", "Blocked"
        );
    }

    public boolean getOnline() {
        return online;
    }

}
