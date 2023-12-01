package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.repository.helpers.GroupChatEachMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

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
        String query = "select * from chat_metadata cm where cm.group_id = ?";
        return jdbcTemplate.query(query, new Object[]{id}, new int[]{Types.INTEGER}, new GroupChatEachMapper());
    }

    public List<Integer> findGroupChatMembers(int id) {
        String query = "select * from gchat_member gm where gm.member_id = ?";
        return jdbcTemplate.query(query, new Object[]{id}, new int[]{Types.INTEGER}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("member_id");
            }
        });
    }
}
