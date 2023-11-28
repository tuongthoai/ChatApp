package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.user.User;
import com.hcmus.chatserver.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    FriendRepository repository;

    public List<User> findAll(int userId) throws Exception {
        return repository.findAll(userId);
    }

    public List<User> findAllFriendOnline(int userId) throws Exception {
        return repository.findAllFriendOnline(userId);
    }

    public boolean addFriend(int userId, int friendId) throws Exception {
        if (userId == friendId) {
            return false;
        }

        // check if block
        return true;
    }
}
