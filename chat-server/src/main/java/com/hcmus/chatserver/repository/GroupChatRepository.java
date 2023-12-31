package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.groupchat.ChatContentDTO;
import com.hcmus.chatserver.entities.groupchat.GroupChat;
import com.hcmus.chatserver.entities.groupchat.GroupChatMember;
import com.hcmus.chatserver.entities.messages.ClientChatMessage;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.helpers.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class GroupChatRepository implements InitializingBean {
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
        transactionTemplate = new TransactionTemplate(transactionManager);
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
        String query = "select * " + "from gchat_member gm " + "join user_metadata um ON gm.member_id = um.user_id " + "join roles r on r.role_id = um.user_role " + "where gm.groupchat_id = ?";
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
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
    }


    public List<ChatContentDTO> getAllMsg(int groupChatId) throws Exception {
        String query = "select tb1.*, um.username " + "from ( " + "select * " + "from gchat_content gc " + "where gc.group_id = ? " + ") as tb1 " + "join user_metadata um on um.user_id = tb1.usersent ";

        return jdbcTemplate.query(query, new Object[]{groupChatId}, new int[]{Types.INTEGER}, new ChatContentDTORowMapper());
    }

    public void persistChatMsg(ClientChatMessage msg) throws Exception {
        String query = "insert into gchat_content(group_id, usersent, msg, msg_offset , senttime) " + "values (?, ?, ?, (select MAX(gc.msg_offset) + 1 from gchat_content gc where gc.group_id = ?), ?)";
        jdbcTemplate.update(query, msg.getGroupChatId(), msg.getUserSentId(), msg.getMsgContent(), msg.getGroupChatId(), System.currentTimeMillis() / 1000);
    }

    public void updateGroupChatName(int groupId, String newName) throws Exception {
        String query = "update gchat_metadata \n" + "set groupname = ?\n" + "where group_id = ?";
        jdbcTemplate.update(query, newName, groupId);
    }

    public void addMember(int groupId, int userId) throws Exception {
        String query = "insert into gchat_member values (?, ?)";
        jdbcTemplate.update(query, groupId, userId);
    }

    public void removeMember(int groupId, int userId) throws Exception {
        String query = "delete from gchat_member \n" + "where gchat_member.member_id = ? and groupchat_id = ?";
        jdbcTemplate.update(query, userId, groupId);
    }

    public Integer findGroupAdminById(int groupId, int userId) throws Exception {
        String query = "select * from gchat_admins ga where ga.group_id = ? and ga.admin_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupId, userId}, new int[]{Types.INTEGER, Types.INTEGER}, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getInt(1);
                }
                return -1;
            }
        });
    }

    public Integer findFirstNotAdmin(int groupId) throws Exception {
        String query = "select * from gchat_member gm where gm.member_id not in (select ga.admin_id from gchat_admins ga where ga.group_id = ?) and gm.groupchat_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupId, groupId}, new int[]{Types.INTEGER, Types.INTEGER}, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getInt(2);
                }
                return -1;
            }
        });
    }

    public void addAdmin(int groupId, int userId) throws Exception {
        String query = "insert into gchat_admins(group_id, admin_id) values (?, ?)";
        jdbcTemplate.update(query, groupId, userId);
    }

    public int create(String chatName, int admin, List<User> members) throws Exception {
        CreateGroupChatTransaction createGroupChatTransaction = new CreateGroupChatTransaction(chatName, admin, members, jdbcTemplate);
        transactionTemplate.execute(createGroupChatTransaction);
        return createGroupChatTransaction.getChatId();
    }

    public int createEmptyGroup(String groupName) {
        String query = "INSERT INTO gchat_metadata (groupname, createdtime, isgroup) VALUES (?, ?, ?) RETURNING group_id";

        return jdbcTemplate.queryForObject(query, Integer.class, groupName, System.currentTimeMillis() / 1000, false);
    }

    public int remove(int chatId) throws Exception {
        RemoveGroupChatTransaction removeGroupChatTransaction = new RemoveGroupChatTransaction(chatId, jdbcTemplate);
        transactionTemplate.execute(removeGroupChatTransaction);
        return 1;
    }

    public int isAdminOf(int chatId, int userId) {
        String query = "select * from gchat_admins ga where ga.group_id = ? and ga.admin_id = ?";
        return jdbcTemplate.query(query, new Object[]{chatId, userId}, new int[]{Types.INTEGER, Types.INTEGER}, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public int updateMemberRole(int groupId, int userId, int role) {
        // admins
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    String checkAdminQuery = "select * from gchat_admins where group_id = ? and admin_id = ?";
                    int row = jdbcTemplate.query(checkAdminQuery, new Object[]{groupId, userId}, new int[]{Types.INTEGER, Types.INTEGER}, new ResultSetExtractor<Integer>() {
                        @Override
                        public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                            if(rs.isBeforeFirst()) {
                                rs.next();
                                return 1;
                            }
                            return 0;
                        }
                    });
                    if (row > 0) {
                        jdbcTemplate.update("delete from gchat_admins where group_id = ? and admin_id = ?", groupId, userId);
                    } else {
                        jdbcTemplate.update("insert into gchat_admins(group_id, admin_id) values (?, ?)", groupId, userId);
                    }
                    System.out.println(row);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException(e);
                }
            }
        });
        return 1;
    }

    public int countMembers(int groupId) {
        String query = "select count(*) from gchat_member gm where gm.groupchat_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupId}, new int[]{Types.INTEGER}, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getInt(1);
                }
                return -1;
            }
        });
    }

    public boolean isGroup(int groupId) {
        String query = "select isgroup from gchat_metadata where group_id = ?";
        return jdbcTemplate.query(query, new Object[]{groupId}, new int[]{Types.INTEGER}, new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getBoolean(1);
                }
                return false;
            }
        });
    }

    public int getOtherMemberId(int groupId, int userId) {
        String query = "select member_id from gchat_member where groupchat_id = ? and member_id != ?";
        return jdbcTemplate.query(query, new Object[]{groupId, userId}, new int[]{Types.INTEGER, Types.INTEGER}, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getInt(1);
                }
                return -1;
            }
        });
    }
}