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
public class LoginHistoryService implements InitializingBean {
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;
    private ObjectMapper mapper;
    public List<Long> getLoginTime() throws Exception{
        return loginHistoryRepository.getAllLoginTime();
    }

    public List<UserLoginTimeEntry> getUserLoginTime() throws Exception{
        return loginHistoryRepository.getAllUserLoginTime();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }
}
