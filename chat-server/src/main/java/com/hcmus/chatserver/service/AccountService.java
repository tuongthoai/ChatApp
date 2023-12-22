package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.api.LogginRequest;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private LoginHistoryService loginHistoryService;

    public int authenticateUser(String userName, String passWord) throws Exception {
        int user_id = -1;
        try {
            user_id = repository.validateUsrPwd(userName, passWord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user_id;
    }

    public int registerUser(String username, String password, String email) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCreatedTime(new java.util.Date().toInstant().getEpochSecond());
        user.setRole("user");
        int usrId = -1;
        try {
            usrId = repository.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return usrId;
    }
}
