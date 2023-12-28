package com.hcmus.chatserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.entities.user.UserDTO;
import com.hcmus.chatserver.entities.user.UserStatisticSummary;
import com.hcmus.chatserver.repository.UserRepository;
import com.hcmus.chatserver.entities.user.UserActivity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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

    public List<User> getNewUser(BigInteger startDate, BigInteger endDate) throws Exception {
        return userRepository.getNewUser(startDate, endDate);
    }

    public List<UserActivity> getUserActivity(BigInteger startDate, BigInteger endDate) throws Exception {
        return userRepository.getUserActivity(startDate, endDate);
    }

    public List<UserDTO> getDirInDirFriend() throws Exception {
        return userRepository.getDirInDirFriend();
    }

    public UserStatisticSummary getStatisticSummary(int userId) throws Exception {
        return userRepository.getStatisticSummary(userId);
    }

    public long countUserFriends(int userId) throws Exception {
        return userRepository.countFriends(userId);
    }
    public void updateUserStatus(int userId, boolean isOnline) throws Exception {
        userRepository.updateUserOnlineStatus(userId, isOnline);
    }

    public boolean isUserBlocked(int userId) throws Exception {
        return userRepository.isUserBlocked(userId);
    }

    public boolean isBlockedBy(int userId, int blockedBy) throws Exception {
        return userRepository.isBlockedBy(userId, blockedBy);
    }

    public void blockUser(int userId, int blockedBy) throws Exception {
        userRepository.blockUser(userId, blockedBy);
    }

    public void unblockUser(int userId, int blockedBy) throws Exception {
        userRepository.unblockUser(userId, blockedBy);
    }

    public List<User> getBlockedUsers(int userId) throws Exception {
        return userRepository.getBlockedUsers(userId);
    }
}
