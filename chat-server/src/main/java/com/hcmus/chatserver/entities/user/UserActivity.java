package com.hcmus.chatserver.entities.user;

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

    public UserActivity(int id, String username, String password, String name, String email, String sex, String address, long birthday, long createdTime, boolean online, boolean blocked, int loginCount, int chatWithCount, int chatGroupCount) {
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
        this.loginCount = loginCount;
        this.chatWithCount = chatWithCount;
        this.chatGroupCount = chatGroupCount;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getChatWithCount() {
        return chatWithCount;
    }

    public void setChatWithCount(int chatWithCount) {
        this.chatWithCount = chatWithCount;
    }

    public int getChatGroupCount() {
        return chatGroupCount;
    }

    public void setChatGroupCount(int chatGroupCount) {
        this.chatGroupCount = chatGroupCount;
    }
}
