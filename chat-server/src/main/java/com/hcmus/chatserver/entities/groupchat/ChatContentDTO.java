package com.hcmus.chatserver.entities.groupchat;

public class ChatContentDTO {
    private int group_id;
    private int usersent;
    private String msg;
    private int msg_offset;
    private long senttime;
    private String username;

    public ChatContentDTO() {
    }

    public ChatContentDTO(int group_id, int usersent, String msg, int msg_offset, long senttime, String username) {
        this.group_id = group_id;
        this.usersent = usersent;
        this.msg = msg;
        this.msg_offset = msg_offset;
        this.senttime = senttime;
        this.username = username;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getUsersent() {
        return usersent;
    }

    public void setUsersent(int usersent) {
        this.usersent = usersent;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsg_offset() {
        return msg_offset;
    }

    public void setMsg_offset(int msg_offset) {
        this.msg_offset = msg_offset;
    }

    public long getSenttime() {
        return senttime;
    }

    public void setSenttime(long senttime) {
        this.senttime = senttime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
