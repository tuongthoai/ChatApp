package com.hcmus.chatserver.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.entities.groupchat.GroupChatEachMapper;
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
        System.out.println(new ObjectMapper().writeValueAsString(getGroupChat(1)));
    }

    public GroupChat getGroupChat(int id) throws Exception {
        String query = "select * from chat_metadata cm where cm.groupid = %d";
        return jdbcTemplate.query(String.format(query, id), new GroupChatEachMapper());
    }
}
