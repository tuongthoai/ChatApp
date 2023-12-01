package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEachMapper implements ResultSetExtractor<User> {
    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = null;
        if (rs.isBeforeFirst()) {
            user = new User();
            rs.next();
            user.setId(rs.getInt("user_id"));
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

        }
        return user;
    }
}
