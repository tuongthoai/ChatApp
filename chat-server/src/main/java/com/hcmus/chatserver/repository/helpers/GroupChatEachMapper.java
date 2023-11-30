package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupChatEachMapper implements ResultSetExtractor<GroupChat> {
    @Override
    public GroupChat extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs.getRow() != 0 && rs.isBeforeFirst()) {
            GroupChat groupChat = new GroupChat();
            groupChat.setGroupId(rs.getInt("groupid"));
            groupChat.setGroupName(rs.getString("groupname"));
            groupChat.setCreatedTime(rs.getLong("createdtime"));
            groupChat.setGroup(rs.getBoolean("isgroup"));
            return groupChat;
        }
        return null;
    }
}
