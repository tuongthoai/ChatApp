package com.hcmus.chatserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements InitializingBean {
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper mapper;
    public String getUser(int id) throws Exception {
        return mapper.writeValueAsString(userRepository.findUserById(id));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }
}