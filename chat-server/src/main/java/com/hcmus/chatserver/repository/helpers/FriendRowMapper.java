package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.user.Friend;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRowMapper implements RowMapper<Friend>{
    @Override
    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friend friend = new Friend();
        friend.setId(rs.getInt("user_id"));
        friend.setUsername(rs.getString("username"));
        friend.setName(rs.getString("fullname"));
        friend.setSex(rs.getString("sex"));
        friend.setAddress(rs.getString("user_address"));
        friend.setBirthday(rs.getLong("birthday"));
        friend.setOnline(rs.getBoolean("isonline"));
        friend.setBlocked(rs.getBoolean("isblocked"));
        return friend;
    }
}
