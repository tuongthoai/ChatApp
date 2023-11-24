package com.hcmus.chatserver.entities.userfriend;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendShipEntryRowMapper implements RowMapper<FriendShipEntry> {
    @Override
    public FriendShipEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendShipEntry result = new FriendShipEntry();
        result.setFriendId(rs.getInt("userid"));
        result.setUserId(rs.getInt("friendid"));
        return result;
    }
}
