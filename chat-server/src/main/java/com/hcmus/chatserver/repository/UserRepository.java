package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.entities.user.UserActivity;
import com.hcmus.chatserver.entities.user.UserDTO;
import com.hcmus.chatserver.entities.user.UserStatisticSummary;
import com.hcmus.chatserver.repository.helpers.*;
import com.hcmus.chatserver.utils.AdminBlockCache;
import com.hcmus.chatserver.utils.UserBlockCache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserRepository implements InitializingBean {
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User findUserById(int userId) {
        String query = "select * from user_metadata um where um.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new UserEachMapper());
    }

    public List<User> findUser(String fullName, String userName, Boolean loginStatus) {
        StringBuilder queryBuilder = new StringBuilder("select * from user_metadata where 1=1");
        ArrayList<Object> argsList = new ArrayList<>();
        ArrayList<Integer> typesList = new ArrayList<>();
        if (fullName != null) {
            queryBuilder.append("AND fullname like ?");
            argsList.add("%" + fullName + "%");
            typesList.add(Types.NVARCHAR);
        }

        if (userName != null) {
            queryBuilder.append("and username like ?");
            argsList.add("%" + userName + "%");
            typesList.add(Types.NVARCHAR);
        }

        if (loginStatus != null) {
            queryBuilder.append("and isonline = ?");
            argsList.add(loginStatus);
            typesList.add(Types.BOOLEAN);
        }

        Object[] args = argsList.toArray(new Object[0]);
        int[] types = typesList.stream().mapToInt(Integer::intValue).toArray();
        return jdbcTemplate.query(queryBuilder.toString(), args, types, new UserRowMapper());
    }

    public int addUser(User user) throws Exception {
        String sql = "INSERT INTO USER_METADATA (USER_ROLE, USERNAME, USER_PASSWORD, FULLNAME, USER_ADDRESS, BIRTHDAY, SEX, EMAIL) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, 2, user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getBirthday(), user.getSex(), user.getEmail());
        return user.getId();
    }

    public void removeUser(int userId) throws Exception {
        String sql = "delete from user_metadata where user_id = %d";
        jdbcTemplate.execute(String.format(sql, userId));
    }

    public int validateUsrPwd(String userName, String password) throws Exception {
        String sql = "select * from user_metadata um where um.username = ? and um.user_password = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, new Object[]{userName, password}, new int[]{Types.VARCHAR, Types.VARCHAR}, new UserRowMapper());
            if (!users.isEmpty()) {
                return users.get(0).getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Logging Failed");
        }

        return -1;
    }

    public int findUserByUsername(String username) throws Exception {
        String query = "select um.* from user_metadata um where um.username = ?";
        User user = jdbcTemplate.query(query, new Object[]{username}, new int[]{Types.VARCHAR}, new UserEachMapper());
        if (user == null) return -1;
        return user.getId();
    }

    public boolean userBlock(int userId, int targetId) {
        return true;
    }

    public List<User> findAll() throws Exception {
        String query = "select * from user_metadata where user_id != 0";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    public void updateUser(User user) throws Exception {
        String query = "update user_metadata set user_role = ?, username = ?, user_password = ?, fullname = ?, user_address = ?, birthday = ?, sex = ?, email = ? where user_id = ?";
        jdbcTemplate.update(query, 2, user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getBirthday(), user.getSex(), user.getEmail(), user.getId());
    }

    // admin block user
    public void adminBlockUser(int userId) throws Exception {
        String query = "update user_metadata set isblocked = not isblocked where user_id = ?";
        jdbcTemplate.update(query, userId);
        boolean isBlocked = isUserBlocked(userId);
        if (isBlocked) {
            AdminBlockCache.add(userId);
        } else {
            AdminBlockCache.remove(userId);
        }
    }

    public List<Long> getAllCreatedTime() throws Exception {
        String query = "select createdtime from user_metadata";
        try {
            return jdbcTemplate.queryForList(query, Long.class);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to retrieve created times");
        }
    }

    public List<User> getNewUser(BigInteger startDate, BigInteger endDate) {
        String query = "select * from user_metadata where createdtime between ? and ?";
        try {
            // system out the query after adding parameter
            System.out.println(startDate);
            System.out.println(endDate);

            return jdbcTemplate.query(query, new Object[]{startDate, endDate}, new int[]{Types.BIGINT, Types.BIGINT}, new UserRowMapper());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to retrieve new users");
        }
    }

    public List<UserActivity> getUserActivity(BigInteger start, BigInteger end) {
        String timeQuery = "WHERE logintime BETWEEN " + start + " AND " + end;
        if (start.compareTo(BigInteger.ZERO) == 0 || end.compareTo(BigInteger.ZERO) == 0) {
            timeQuery = "";
        }
        // get all user active in a period of time
        String query = "WITH active_user AS (\n" +
                "    SELECT user_id, logintime\n" +
                "    FROM login_history\n" + timeQuery +
                ")\n" +
                "\n" +
                "SELECT\n" +
                "    usr.*,\n" +
                "    COUNT(DISTINCT login_history.logintime) AS logincount,\n" +
                "    COUNT(DISTINCT gchat_member.member_id) AS chatwithcount,\n" +
                "    COUNT(DISTINCT user_gchat_list.group_id) AS chatgroupcount\n" +
                "FROM user_metadata usr\n" +
                "JOIN active_user ON usr.user_id = active_user.user_id\n" +
                "LEFT JOIN login_history on usr.user_id = login_history.user_id\n" +
                "LEFT JOIN user_gchat_list ON usr.user_id = user_gchat_list.user_id\n" +
                "LEFT JOIN gchat_member ON user_gchat_list.group_id = gchat_member.groupchat_id\n" +
                "GROUP BY usr.user_id\n" +
                "ORDER BY usr.user_id";
        try {
            return jdbcTemplate.query(query, new UserActivityRowMapper());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to retrieve user activity");
        }
    }
    public List<UserDTO> getDirInDirFriend() throws Exception {
        String query = "select \n" +
                "\tme.user_id as user_id,\n" +
                "\tme.username as username,\n" +
                "\tme.fullname as fullname,\n" +
                "\tme.sex as sex,\n" +
                "\tme.createdtime as createdtime,\n" +
                "\tcount (distinct friend1.friend_id) as directfriend, \n" +
                "\t(count (distinct friend1.friend_id) + count (distinct friend2.friend_id)) as indirectfriend\n" +
                "from user_metadata me \n" +
                "join user_friend friend1 on me.user_id = friend1.user_id \n" +
                "join user_friend friend2 on (\n" +
                "\tfriend1.friend_id = friend2.user_id AND\n" +
                "\tfriend2.friend_id != me.user_id) \n" +
                "where friend2.friend_id NOT IN (select friend.friend_id \n" +
                "\t\t\t\t\t\t\t  \tfrom user_metadata me1 \n" +
                "\t\t\t\t\t\t\t  \tjoin user_friend friend on me1.user_id = friend.user_id \n" +
                "\t\t\t\t\t\t\t  \twhere me1.user_id = me.user_id\n" +
                "\t\t\t\t\t\t\t \t)\t\n" +
                "group by me.user_id\n" +
                "order by me.user_id\n";
        try {
            return jdbcTemplate.query(query, new UserDTORowMapper());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to retrieve direct - indirect friend");
        }
    }

    public UserStatisticSummary getStatisticSummary(int userId) {
        String query = "";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new ResultSetExtractor<UserStatisticSummary>() {
            @Override
            public UserStatisticSummary extractData(ResultSet rs) throws SQLException, DataAccessException {
                UserStatisticSummary result = new UserStatisticSummary();
                if (rs.next()) {
                    result.setLastLogin(rs.getLong("last_login"));
                    result.setNoFiends(rs.getLong("no_friends"));
                    result.setNoGroupChat(rs.getLong("no_group_chat"));
                    return result;
                }
                return null;
            }
        });
    }

    public long countFriends(int userId) throws Exception {
        String query = "select count(*) from user_friend uf where uf.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    return rs.getLong(1);
                }

                return Long.valueOf(0);
            }
        });
    }

    public void updateUserOnlineStatus(int userId, boolean isOnline) throws Exception {
        String query  = "update user_metadata set isonline = ? where user_metadata.user_id = ?";
        jdbcTemplate.update(query, isOnline, userId);
    }

    public String resetPassword(String email) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String newPassword = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String query = "update user_metadata set user_password = '" + newPassword + "' where email = '" + email + "'";
        jdbcTemplate.update(query);
        return newPassword;
    }

    public boolean isUserBlocked(int userId) {
        String query = "select isblocked from user_metadata where user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new int[]{Types.INTEGER}, new ResultSetExtractor<Boolean>() {
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

    // if user1 is blocked by user2 or vice versa?
    public boolean isBlockedBy(int user1, int user2) {
        String query = "select * from user_block_list where (user_id = ? and userisblocked = ?) or (user_id = ? and userisblocked = ?)";
        // return true if there is a row in user_block_id table that contains user1 and user2
        return jdbcTemplate.query(query, new Object[]{user1, user2, user2, user1}, new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER}, new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.isBeforeFirst()) {
                    return true;
                }

                return false;
            }
        });
    }

    public void blockUser(int user1, int user2) {
        // if user1 and user2 are friends, delete the row in user_friend table
        String query1 = "delete from user_friend where (user_id = ? and friend_id = ?) or (user_id = ? and friend_id = ?)";
        jdbcTemplate.update(query1, user1, user2, user2, user1);

        String query = "insert into user_block_list (user_id, userisblocked) values (?, ?)";
        jdbcTemplate.update(query, user1, user2);
        UserBlockCache.add(user1, user2);
    }
    public void unblockUser(int user1, int user2) {
        String query = "delete from user_block_list where user_id = ? and userisblocked = ?";
        jdbcTemplate.update(query, user1, user2);
        UserBlockCache.remove(user1, user2);
    }

    // get all users who are blocked by userId or block userId
    public List<User> getBlockedUsers(int userId) {
        String query = "select * from user_metadata where user_id in (select userisblocked from user_block_list where user_id = ?) or user_id in (select user_id from user_block_list where userisblocked = ?)";
        return jdbcTemplate.query(query, new Object[]{userId, userId}, new int[]{Types.INTEGER, Types.INTEGER}, new UserRowMapper());
    }

    public List<User> getAdminBlockedUsers() {
        String query = "select * from user_metadata where isblocked = true";
        return jdbcTemplate.query(query, new UserRowMapper());
    }
}
