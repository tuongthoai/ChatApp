package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.repository.helpers.LoginHistoryEntry;
import com.hcmus.chatserver.repository.helpers.LoginHistoryRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
@Repository
public class LoginHistoryRepository implements InitializingBean {
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public LoginHistoryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<LoginHistoryEntry> findAllBy(int userId) throws Exception {
        String query = "select * from login_history lh where lh.userid = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new LoginHistoryRowMapper());
    }

    public List<LoginHistoryEntry> findAll() throws Exception {
        String query = "select * from login_history";
        return jdbcTemplate.query(query, new LoginHistoryRowMapper());
    }

    public List<Long> getAllLoginTime(){
        String query = "select logintime from login_history";
        try {
            return jdbcTemplate.queryForList(query, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve created times");
        }
    }
}
