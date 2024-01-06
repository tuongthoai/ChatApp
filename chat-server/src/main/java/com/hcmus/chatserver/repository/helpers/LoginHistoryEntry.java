package com.hcmus.chatserver.repository.helpers;


public class LoginHistoryEntry {
    private long userId;
    private long loginTime;
    private long dcTime;

    public LoginHistoryEntry() {
    }

    public LoginHistoryEntry(long userId, long loginTime, long dcTime) {
        this.userId = userId;
        this.loginTime = loginTime;
        this.dcTime = dcTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getDcTime() {
        return dcTime;
    }

    public void setDcTime(long dcTime) {
        this.dcTime = dcTime;
    }
}
