package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.user.UserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDTORowMapper implements RowMapper<UserDTO> {
    @Override
    public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDTO userdto = new UserDTO();
        userdto.setId(rs.getInt("user_id"));
        userdto.setUsername(rs.getString("username"));
        userdto.setName(rs.getString("fullname"));
        userdto.setSex(rs.getString("sex"));
        userdto.setCreatedTime(rs.getLong("createdtime"));
        userdto.setFriends(rs.getInt("directfriend"));
        userdto.setAcquaintances(rs.getInt("indirectfriend"));
        return userdto;
    }
}
