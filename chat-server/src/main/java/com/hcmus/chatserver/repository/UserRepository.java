package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.User;
import com.hcmus.chatserver.entities.UserRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
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

    public User getUserById(int userId) throws Exception{
        String query = "select * from user_metadata um where um.userid = 1";
        return jdbcTemplate.queryForObject(query, new UserRowMapper());
    }
}
