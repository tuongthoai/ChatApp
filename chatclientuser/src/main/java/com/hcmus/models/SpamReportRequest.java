package com.hcmus.models;

public class SpamReportRequest {
    private int userIdSent;
    private int userIdReported;
    private String content;
    public SpamReportRequest(int userId, int reportedUserId, String content) {
        this.userIdSent = userId;
        this.userIdReported = reportedUserId;
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
