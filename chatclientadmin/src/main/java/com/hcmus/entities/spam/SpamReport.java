package com.hcmus.entities.spam;

import java.util.Arrays;
import java.util.List;

public class SpamReport {
    private int reportId;
    private int senderId;
    private String senderName;
    private int reportedUserId;
    private String reportedUsername;
    private long createdTime;
    private String content;
    private String status;

    public SpamReport(int reportId, int senderId, String senderName, int reportedUserId, String reportedUsername, long createdTime, String content, String status) {
        this.reportId = reportId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.reportedUserId = reportedUserId;
        this.reportedUsername = reportedUsername;
        this.createdTime = createdTime;
        this.content = content;
        this.status = status;
    }

    public SpamReport() {
    }

    public static List<String> getColumnNames() {
        return Arrays.asList(
                "Report Id",
                "Sender Name",
                "Reported Username",
                "Report Time",
                "Content"
        );
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(int reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
