package com.hcmus.chatserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.repository.LoginHistoryRepository;
import com.hcmus.chatserver.repository.helpers.LoginHistoryEntry;
import com.hcmus.chatserver.repository.helpers.UserLoginTimeEntry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginHistoryService {
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    public List<Long> getLoginTime() throws Exception {
        return loginHistoryRepository.getAllLoginTime();
    }

    public List<UserLoginTimeEntry> getUserLoginTime() throws Exception {
        return loginHistoryRepository.getAllUserLoginTime();
    }

    public long getLastLoginOf(int userId) throws Exception {
        return loginHistoryRepository.getLastLogin(userId);
    }

    public void persistLoginInfo(int userId) throws Exception {}

    public List<LoginHistoryEntry> findAllBy(int userId) throws Exception {
        return loginHistoryRepository.findAllBy(userId);
    }

    public void setLoginTime(int userId) throws Exception {
        loginHistoryRepository.setLoginTime(userId);
    }

    public void setDisconnectTime(int userId) throws Exception {
        loginHistoryRepository.setDisconnectTime(userId);
    }
}
