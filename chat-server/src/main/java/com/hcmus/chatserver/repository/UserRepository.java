package com.hcmus.chatserver.repository;

import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.helpers.UserEachMapper;
import com.hcmus.chatserver.repository.helpers.UserRowMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
        String query = "select * from user_metadata um where um.userid = ?";
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
        String sql = "INSERT INTO USER_METADATA (USERNAME, USER_PASSWORD, FULLNAME, USER_ADDRESS, BIRTHDAY, SEX, EMAIL, CREATEDTIME) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getBirthday(), user.getSex(), user.getEmail(), user.getCreatedTime());
        return user.getId();
    }

    public void removeUser(int userId) {
        String sql = "delete from user_metadata where userid = %d";
        jdbcTemplate.execute(String.format(sql, userId));
    }

    public int validateUsrPwd(String userName, String password) throws Exception {
        String sql = "select * from user_metadata um where um.username = ? and um.password = ?";
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
}
