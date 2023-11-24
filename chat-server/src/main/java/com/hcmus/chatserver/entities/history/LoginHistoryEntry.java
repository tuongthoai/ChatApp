package com.hcmus.chatserver.entities.history;

public class LoginHistoryEntry {
    private long userId;
    private long loginTime;
    private long dctime;

    public LoginHistoryEntry() {
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

    public long getDctime() {
        return dctime;
    }

    public void setDctime(long dctime) {
        this.dctime = dctime;
    }
}
