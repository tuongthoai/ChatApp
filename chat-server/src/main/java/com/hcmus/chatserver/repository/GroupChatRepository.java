package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.repository.helpers.GroupChatEachMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;

@Repository
public class GroupChatRepository implements InitializingBean {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GroupChat findGroupChatById(int id) throws Exception {
        String query = "select * from chat_metadata cm where cm.groupid = ?";
        return jdbcTemplate.query(query, new Object[]{id}, new int[]{Types.INTEGER}, new GroupChatEachMapper());
    }
}
