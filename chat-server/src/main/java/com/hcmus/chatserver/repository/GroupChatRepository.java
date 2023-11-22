package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.entities.user.UserRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class GroupChatRepository implements InitializingBean {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GroupChat getGroupChat(int id) {
        String query = "";
        return null;
    }
}
