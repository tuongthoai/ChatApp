package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.spam.SpamReport;
import com.hcmus.chatserver.repository.helpers.SpamRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SpamRepository implements InitializingBean {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public List<SpamReport> findAll() {
        List<SpamReport> spamReports = null;
        String query = "select sr.*, um1.username as sender, um2.username as reported_user from spam_report sr join user_metadata um1 on sr.usersent = um1.user_id join user_metadata um2 on sr.userisreported = um2.user_id";
        return jdbcTemplate.query(query, new SpamRowMapper());
    }

    public void insertReport(int userSentId, int userIdReported, String content) throws Exception {
        String query = "insert into spam_report (usersent, userisreported, \"content\", status, createdtime) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, userSentId, userIdReported, content, "PENDING", System.currentTimeMillis());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
