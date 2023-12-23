package com.hcmus.chatserver.entities.user;

public class Friend {
    private int id;
    private String username;
    private String name;
    private String sex;
    private String address;
    private long birthday;
    private boolean online = false;
    private boolean blocked = false;

    public Friend() {
    }

    public Friend(int id, String username, String name,  String sex, String address, long birthday, boolean online, boolean blocked){
        this.id = id;
        this.username = username;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.birthday = birthday;
        this.online = online;
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
