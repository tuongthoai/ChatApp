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
        List<User> data = repository.findAll(userId);
//        System.out.println(data);
        return data;
    }

    public List<User> findAllFriendOnline(int userId) throws Exception {
        return repository.findAllFriendOnline(userId);
    }

    public void addFriend(int userId, int friendId) throws Exception {
        repository.addFriend(userId, friendId);
    }

    public long countFriendsOf(int userId) throws Exception {
        return repository.countFriendOf(userId);
    }

    public List<User> getFriendNotInGroup(int userId, int groupId) throws Exception {
        return repository.getFriendNotInGroup(userId, groupId);
    }

    public List<User> findAllStranger(int userId) throws Exception {
        return repository.findAllStranger(userId);
    }
    public void removeFriend(int userId, int friendId) throws Exception {
        repository.removeFriend(userId, friendId);
    }
}
