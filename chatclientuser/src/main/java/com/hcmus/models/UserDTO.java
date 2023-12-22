package com.hcmus.models;

public class UserDTO {
    private int id;
    private String username;
    private String fullname;
    private String online;
    public UserDTO(){}
    public UserDTO(int id, String username, String fullname, String online) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.online = online;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
