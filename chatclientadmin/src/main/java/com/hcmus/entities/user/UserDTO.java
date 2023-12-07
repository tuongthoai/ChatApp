package com.hcmus.entities.user;

public class UserDTO {
    private int id;
    private String username;
    private String name;
    private String sex;
    private long createdTime;
    private int friends;
    private int acquaintances;

    public UserDTO(){}
    public UserDTO(int id, String username, String name, String sex, long createdTime, int friends, int acquaintances) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.sex = sex;
        this.createdTime = createdTime;
        this.friends = friends;
        this.acquaintances = acquaintances;
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

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public int getAcquaintances() {
        return acquaintances;
    }

    public void setAcquaintances(int acquaintances) {
        this.acquaintances = acquaintances;
    }
}
