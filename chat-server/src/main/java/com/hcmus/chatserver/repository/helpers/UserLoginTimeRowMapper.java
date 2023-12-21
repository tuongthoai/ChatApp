package com.hcmus.chatserver.repository.helpers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginTimeRowMapper implements RowMapper<UserLoginTimeEntry> {
    @Override
    public UserLoginTimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLoginTimeEntry result = new UserLoginTimeEntry();
        result.setLoginTime(rs.getLong("logintime"));
        result.setUsername(rs.getString("username"));
        result.setFullname(rs.getString("fullname"));
        return result;
    }
}
