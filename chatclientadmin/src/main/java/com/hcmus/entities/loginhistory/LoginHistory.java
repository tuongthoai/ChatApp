package com.hcmus.entities.loginhistory;

import java.util.Arrays;
import java.util.List;

public class LoginHistory {
    private int userId;
    private long loginTime;
    private long dcTime;

    public static List<String> getColumnNames() {
        return Arrays.asList("User Id", "Login Time", "Disconnect Time");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
