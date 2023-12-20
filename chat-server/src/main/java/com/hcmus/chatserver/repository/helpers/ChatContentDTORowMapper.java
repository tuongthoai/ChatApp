package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.groupchat.ChatContentDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatContentDTORowMapper implements RowMapper<ChatContentDTO> {

    @Override
    public ChatContentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChatContentDTO result = new ChatContentDTO();
        result.setGroup_id(rs.getInt("group_id"));
        result.setMsg(rs.getString("msg"));
        result.setMsg_offset(rs.getInt("msg_offset"));
        result.setSenttime(rs.getLong("senttime"));
        result.setUsername(rs.getString("username"));
        result.setUsersent(rs.getInt("usersent"));
        return result;
    }
}
