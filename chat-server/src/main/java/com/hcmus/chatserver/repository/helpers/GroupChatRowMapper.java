package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupChatRowMapper implements RowMapper<GroupChat> {
    @Override
    public GroupChat mapRow(ResultSet rs, int rowNum) throws SQLException {
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupId(rs.getInt("group_id"));
        groupChat.setGroupName(rs.getString("groupname"));
        groupChat.setCreatedTime(rs.getLong("createdtime"));
        groupChat.setGroup(rs.getBoolean("isgroup"));
        return groupChat;
    }
}
