package com.hcmus.chatserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements InitializingBean {
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper mapper;
    public String getUser(int id) throws Exception {
        return mapper.writeValueAsString(userRepository.findUserById(id));
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

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }
}
