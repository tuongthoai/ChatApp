package com.hcmus.chatserver.repository.helpers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHistoryRowMapper implements RowMapper<LoginHistoryEntry> {
    @Override
    public LoginHistoryEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoginHistoryEntry result = new LoginHistoryEntry();
        result.setLoginTime(rs.getLong("logintime"));
        result.setUserId(rs.getLong("user_id"));
        result.setDcTime(rs.getLong("dctime"));
        return result;
    }
}
