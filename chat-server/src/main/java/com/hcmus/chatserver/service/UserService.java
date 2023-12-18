package com.hcmus.chatserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.entities.user.UserDTO;
import com.hcmus.chatserver.repository.UserRepository;
import com.hcmus.chatserver.repository.helpers.UserActivityEntry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService implements InitializingBean {
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper mapper;

    public User getUser(int id) throws Exception {
        return userRepository.findUserById(id);
    }

    public List<User> findAll() throws Exception {
        return userRepository.findAll();
    }

    public int addUser(User user) throws Exception {
        int userId = -1;
//        System.out.println("UserService: " + user.getBirthday());
        try {
            userId = userRepository.addUser(user);
        } catch (Exception e) {
            System.out.println("UserService: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return userId;
    }

    public void removeUser(int userId) throws Exception {
        userRepository.removeUser(userId);
    }

    public void updateUser(User user) throws Exception {
        userRepository.updateUser(user);
    }

    public void adminBlockUser(int userId) throws Exception {
        userRepository.adminBlockUser(userId);
    }

    public List<Long> getCreatedTime() throws Exception {
        return userRepository.getAllCreatedTime();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }

    // format date: yyyy-mm-dd
    public List<User> getNewUser(String startDate, String endDate) throws Exception {
        // convert to big integer
        Timestamp startTimestamp = Timestamp.valueOf(startDate + " 00:00:00");
        Timestamp endTimestamp = Timestamp.valueOf(endDate + " 23:59:59");
        BigInteger start = new BigInteger(String.valueOf(startTimestamp.toInstant().getEpochSecond()));
        BigInteger end = new BigInteger(String.valueOf(endTimestamp.toInstant().getEpochSecond()));

        return userRepository.getNewUser(start, end);
    }

    public List<UserActivityEntry> getUserActivity(String startDate, String endDate) throws Exception {
        // convert to big integer
        Timestamp startTimestamp = Timestamp.valueOf(startDate + " 00:00:00");
        Timestamp endTimestamp = Timestamp.valueOf(endDate + " 23:59:59");
        BigInteger start = new BigInteger(String.valueOf(startTimestamp.toInstant().getEpochSecond()));
        BigInteger end = new BigInteger(String.valueOf(endTimestamp.toInstant().getEpochSecond()));

        return userRepository.getUserActivity(start, end);
    }

    public List<UserDTO> getDirInDirFriend() throws Exception {
        return userRepository.getDirInDirFriend();
    }
}
