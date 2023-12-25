package com.hcmus.chatserver.repository.helpers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public class RemoveGroupChatTransaction extends TransactionCallbackWithoutResult {
    private final JdbcTemplate jdbcTemplate;
    private final int chatId;

    public RemoveGroupChatTransaction(int chatId, JdbcTemplate jdbcTemplate) {
        this.chatId = chatId;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected void doInTransactionWithoutResult(TransactionStatus status) {
        try {
            String[] queries = new String[]{"delete from gchat_content gc where gc.group_id = ?", "delete from gchat_admins a where a.group_id = ?", "delete from gchat_member b where b.groupchat_id = ?", "delete from gchat_metadata c where c.group_id = ?"};

            for (int i = 0; i < queries.length; ++i) {
                jdbcTemplate.update(queries[i], chatId);
            }
        } catch (Exception e) {
            status.setRollbackOnly();
            throw new RuntimeException(e);
        }
    }
}
