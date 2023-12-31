package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.repository.helpers.LoginHistoryEntry;
import com.hcmus.chatserver.repository.helpers.LoginHistoryRowMapper;
import com.hcmus.chatserver.repository.helpers.UserLoginTimeEntry;
import com.hcmus.chatserver.repository.helpers.UserLoginTimeRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "select * from login_history lh where lh.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new LoginHistoryRowMapper());
    }

    public List<LoginHistoryEntry> findAll() throws Exception {
        String query = "select * from login_history";
        return jdbcTemplate.query(query, new LoginHistoryRowMapper());
    }

    public List<Long> getAllLoginTime() {
        String query = "select logintime from login_history";
        try {
            return jdbcTemplate.queryForList(query, Long.class);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to retrieve created times");
        }
    }

    public List<UserLoginTimeEntry> getAllUserLoginTime() {
        String query = "SELECT usr.user_id, usr.username, usr.fullname, l.logintime" + " FROM user_metadata usr " + " INNER JOIN (" + "     SELECT user_id, MAX(logintime) AS logintime" + "     FROM login_history" + "     GROUP BY user_id " + ") l ON usr.user_id = l.user_id" + " ORDER BY l.logintime DESC";
        try {
            return jdbcTemplate.query(query, new UserLoginTimeRowMapper());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to retrieve created times");
        }
    }

    public long getLastLogin(int userId) throws Exception {
        String query = "select COALESCE(MAX(lh.logintime),0) from login_history lh where lh.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
    }

    public void setLoginTime(int userId) {
        String query = "insert into login_history values (?, extract(epoch from current_timestamp), null)";
        jdbcTemplate.update(query, userId);
    }

    public void setDisconnectTime(int userId) {
        String query = "update login_history set dctime = extract(epoch from current_timestamp) where user_id = ? and dctime is null";
        jdbcTemplate.update(query, userId);
    }
}
