package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.entities.groupchat.GroupChatMember;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.helpers.GroupChatEachMapper;
import com.hcmus.chatserver.repository.helpers.GroupChatRowMapper;
import com.hcmus.chatserver.repository.helpers.UserRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
        System.out.println("Done GChat Repo");
    }

    public GroupChat findGroupChatById(int id) throws Exception {
        String query = "select * from gchat_metadata cm where cm.group_id = ?";
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

    public List<GroupChat> findByMemberId(Integer memberId) {
        String query = "select gmet.* from gchat_member gm join gchat_metadata gmet on gmet.group_id = gm.groupchat_id where gm.member_id = ?";
        return jdbcTemplate.query(query, new Object[]{memberId}, new int[]{Types.INTEGER}, new GroupChatRowMapper());
    }

    public List<GroupChat> findAll() throws Exception {
        String query = "select * from gchat_metadata";
        return jdbcTemplate.query(query, new GroupChatRowMapper());
    }

    public List<User> findAllMembers(int groupId) throws Exception {
        String query = "select u.* from gchat_member gm join user_metadata u on u.user_id = gm.member_id where gm.groupchat_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupId}, new int[]{Types.INTEGER}, new UserRowMapper());
    }
    public List<User> findAllAdmins(int groupId) throws Exception {
        String query = "select u.* from gchat_admins ga join user_metadata u on u.user_id = ga.admin_id where ga.group_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupId}, new int[]{Types.INTEGER}, new UserRowMapper());
    }

    public List<GroupChatMember> findMembersOf(int groupChatId) throws Exception {
        String query = "select * " +
                "from gchat_member gm " +
                "join user_metadata um ON gm.member_id = um.user_id " +
                "join roles r on r.role_id = um.user_role " +
                "where gm.groupchat_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupChatId}, new int[]{Types.INTEGER}, new RowMapper<GroupChatMember>() {
            @Override
            public GroupChatMember mapRow(ResultSet rs, int rowNum) throws SQLException {
                GroupChatMember member = new GroupChatMember();
                member.setUserId(rs.getInt("user_id"));
                member.setUsername(rs.getString("username"));
                member.setRole(rs.getString("role_name"));
                return member;
            }
        });
    }

    public long countNoGroupChatOf(int userId) throws Exception {
        String query = "select count(*) from gchat_member gm where gm.member_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
    }
}
