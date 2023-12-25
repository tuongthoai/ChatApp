package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class CreateGroupChatTransaction extends TransactionCallbackWithoutResult {
    private int chatId;
    private String chatName;
    private int admin;
    private List<User> members;
    private final JdbcTemplate jdbcTemplate;

    public CreateGroupChatTransaction(String chatName, int admin, List<User> members, JdbcTemplate jdbcTemplate) {
        this.chatName = chatName;
        this.admin = admin;
        this.members = members;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    protected void doInTransactionWithoutResult(TransactionStatus status) {
        try {
            // Your database operations using JdbcTemplate

            // insert gchat_metadata
            String createChatMetadata = "insert into gchat_metadata(groupname, createdtime) values (?, ?);";
            jdbcTemplate.update(createChatMetadata, chatName, System.currentTimeMillis());

            // insert gchat_admins
            // get gchatid;
            Integer chatId = jdbcTemplate.query("select gm.group_id  from gchat_metadata gm where gm.groupname = ?", new Object[]{chatName}, new int[]{Types.VARCHAR}, new ResultSetExtractor<Integer>() {
                @Override
                public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.isBeforeFirst()) {
                        rs.next();
                        return rs.getInt(1);
                    }
                    return -1;
                }
            });

            // insert gchat_members
            String sql = "insert into gchat_member(groupchat_id, member_id) values (?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User user = members.get(i);
                    ps.setInt(1, chatId);
                    ps.setInt(2, user.getId());
                }

                @Override
                public int getBatchSize() {
                    return members.size();
                }
            });

            jdbcTemplate.update("insert into gchat_admins(group_id, admin_id) values(?, ?)", chatId, admin);
            // If something goes wrong, you can manually trigger a rollback
            // transactionStatus.setRollbackOnly();
            this.chatId = chatId;
        } catch (Exception e) {
            // Handle exception and optionally mark the transaction for rollback
            status.setRollbackOnly();
            throw new RuntimeException(e);
        }
    }
}
