package com.hcmus.chatserver.repository.helpers;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserActivityRowMapper implements RowMapper<UserActivityEntry>{
    @Override
    public UserActivityEntry mapRow(ResultSet rs, int rowNum) throws SQLException{
        UserActivityEntry user = new UserActivityEntry();
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
        user.setLoginCount(rs.getInt("logincount"));
        user.setChatWithCount(rs.getInt("chatwithcount"));
        user.setChatGroupCount(rs.getInt("chatgroupcount"));

        // don't count the user himself
        if (user.getChatWithCount() > 0) {
            user.setChatWithCount(user.getChatWithCount() - 1);
        }

        return user;
    }
}
