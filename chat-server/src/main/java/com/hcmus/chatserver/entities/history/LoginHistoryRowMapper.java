package com.hcmus.chatserver.entities.history;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHistoryRowMapper implements RowMapper<LoginHistoryEntry> {
    @Override
    public LoginHistoryEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoginHistoryEntry result = new LoginHistoryEntry();
        result.setLoginTime(rs.getLong("logintime"));
        result.setUserId(rs.getLong("userid"));
        result.setDctime(rs.getLong("dctime"));
        return result;
    }
}
