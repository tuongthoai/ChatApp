package com.hcmus.chatserver.entities.spam;

public class CreateSpamRequest {
    private int userIdSent;
    private int userIdReported;
    private String content;

    public CreateSpamRequest() {
    }

    public CreateSpamRequest(int userIdSent, int userIdReported, String content) {
        this.userIdSent = userIdSent;
        this.userIdReported = userIdReported;
        this.content = content;
    }

    public int getUserIdSent() {
        return userIdSent;
    }

    public void setUserIdSent(int userIdSent) {
        this.userIdSent = userIdSent;
    }

    public int getUserIdReported() {
        return userIdReported;
    }

    public void setUserIdReported(int userIdReported) {
        this.userIdReported = userIdReported;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
