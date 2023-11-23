package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.entities.user.UserEachMapper;
import com.hcmus.chatserver.entities.user.UserRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository implements InitializingBean {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getUser(int userId) throws Exception {
        String query = "select * from user_metadata um where um.userid = %d";
        return jdbcTemplate.query(String.format(query, userId), new UserEachMapper());
    }

    public Integer validateAccount(String username, String password) {
        String query = "select * from user_metadata um where um.username = %s and um.user_password  = %s";
        User user = jdbcTemplate.query(String.format(query, username, password), new UserEachMapper());
        if (user == null) {
            return -1;
        } else {
            return user.getId();
        }
    }
}
