package com.hcmus.chatserver.entities.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("userid"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("user_password"));
        user.setName(rs.getString("fullname"));
        user.setEmail(rs.getString("email"));
        user.setSex(rs.getString("sex"));
        user.setAddress(rs.getString("user_address"));
        user.setBirthday(rs.getLong("birthday"));
        user.setCreatedTime(rs.getLong("createdtime"));
        user.setOnline(rs.getBoolean("isonline"));
        user.setBlocked(rs.getBoolean("isblocked"));
        return user;
    }
}
