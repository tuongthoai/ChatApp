package com.hcmus.models;

import java.time.LocalDate;

public class Friend {
    private int id;
    private String username;
    private String fullname;
    private String sex;
    private LocalDate birthday;
    public Friend(){}

    public Friend(int id, String username, String fullname, String sex, LocalDate birthday) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.sex = sex;
        this.birthday = birthday;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
