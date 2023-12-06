package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.spam.SpamReport;
import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.FriendRepository;
import com.hcmus.chatserver.repository.SpamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpamService {
    @Autowired
    SpamRepository repository;

    public List<SpamReport> findAll() throws Exception {
        List<SpamReport> data = repository.findAll();
        System.out.println(data);
        return data;
    }
}
