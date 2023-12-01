package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.helpers.UserRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
@Repository
public class FriendRepository implements InitializingBean {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public List<User> findAll(int userId) throws Exception {
        List<User> result = null;
        String query = "select um.* from user_friend uf join user_metadata um ON um.user_id = uf.friend_id where uf.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new UserRowMapper());
    }

    public void addFriend(int userId, int friendId) throws Exception {
        String query = "insert into user_friend(userid, friendid) values (?, ?)";
        // friendship is bidirectional relationship
        jdbcTemplate.update(query, userId, friendId);
        jdbcTemplate.update(query, friendId, userId);
    }

    public void removeFriend(int userId, int friendId) throws Exception {
        String query = "delete from user_friend uf where uf.userid = ? and uf.friendid = ?";
        // friendship is bidirectional relationship
        jdbcTemplate.update(query, userId, friendId);
        jdbcTemplate.update(query, friendId, userId);
    }

    public List<User> findAllFriendOnline(int userId) throws Exception {
        String query = "select * from user_friend uf join user_metadata um on uf.friend_id = um.user_id where uf.user_id = ? and um.isonline = true";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new UserRowMapper());
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
