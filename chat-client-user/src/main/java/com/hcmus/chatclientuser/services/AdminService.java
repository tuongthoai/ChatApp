package com.hcmus.chatclientuser.services;


import com.hcmus.chatclientuser.session.ClientSession;
import com.sun.jdi.ArrayReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.AbstractRabbitListenerContainerFactoryConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hcmus.chatclientuser.entities.User;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {
    private final String API = "localhost:8080/api/admin";
    private final RestTemplate restTemplate;

    public AdminService() {
        this.restTemplate = new RestTemplate();
    }

    public List<User> filterByOnlineTimes(List<User> userList, Integer min, Integer max) {
        List<User> res = new ArrayList<>();
        for (User user : userList) {
            Integer cnt = getNumOfTimesOnlineByTime(user.getId(), start, end);
            if (cnt >= min && cnt <= max) {
                res.add(user);
            }
        }
        return res;
    }

    public ArrayList<Integer> getNumberOfUserByYear(List<User> userList, String year, String criteria) {
        ArrayList<Integer> monthCnt = new ArrayList<Integer>(12);
        if (criteria.equals("CREATEDTIME")) {
            for (User user : userList) {
                if (user.getCreatedTime().getYear() == year) { // thay bang so sanh unix timestamp
                    monthCnt[user.getCreatedTime().getMonth() - 1]++;
                }
            }
            return monthCnt;
        }
        // criteria = "ONLINETIME"
        for (User user : userList) {
            // if user is online during a period of time
            // monthCnt[user.getOnlineTime().getMonth() - 1]++;
            continue;
        }
        return monthCnt;
    }

    public List<User> sortUserOnCriteria(List<User> userList, String criteria) {
        if (criteria.equals("NAME")) {
            // sort by name
            continue;
        }
        // criteria = "CREATEDTIME"
        // sort by created time

        return new ArrayList<>();
    }
    // getUsersByTime(userList, "2020", "CREATEDTIME");

    // 6
    public List<User> getNewUserByTime(String start, String end) { // start and end: unix timestamp
        return restTemplate.exchange(API + "/users/new" + start + "/" + end, HttpMethod.GET, null, List.class).getBody();
    }

    // 7
    public List<User> getFriendsOfUser(String id) {
        return restTemplate.exchange(API + "/users/" + id + "/friends", HttpMethod.GET, null, List.class).getBody();
    }

    public Integer getNumberOfFriendsOfUser(String id) {
        return getFriendsOfUser(id).size();
    }

    public Integer getNumberOfIndirectFriendsOfUser(List<User> friends, String id) {
        Integer cnt = 0;
        for (User friend : friends) {
            cnt += getNumberOfFriendsOfUser(friend.getId());
        }
        return cnt;
    }

    public List<String> getAllFullnames(List<User> userList) {
        List<String> names = new ArrayList<>();
        for (User user : userList) {
            names.add(user.getName());
        }
        return names;
    }

    public List<String> getAllExistingNames(List<User> userList) {
        List<String> names = getAllFullnames(userList);

        for (String name : names) {
            name = name.split(" ")[-1]; // bỏ họ + tên đệm
        }

        return names;
    }

    public List<User> filterUserByName(List<User> userList, List<String> names) {
        List<User> res = new List<User>();
        for (User user : userList) {
            if (names.contains(user.getName().split(" ")[-1])) {
                res.add(user);
            }
        }
        return res;
    }

    public List<User> filterByNumberOfX(List<User> userList, Integer min, Integer max, String criteria) {
        List<User> res = new List<User>();
        if (criteria.equals("FRIENDS")) {
            for (User user : userList) {
                Integer cnt = getNumberOfFriendsOfUser(user.getId());
                if (cnt >= min && cnt <= max) {
                    res.add(user);
                }
            }
            return res;
        }
        // criteria = "SOME OTHER CRITERIA"

        return res;
    }

    // 8
    // return the list of user who is online during a period of time
    public List<User> getUserOnlineByTime(String start, String end) { // start and end: unix timestamp
        return restTemplate.exchange(API + "/users/online/" + start + "/" + end + "/online", HttpMethod.GET, null, List.class).getBody();
    }

    public Integer getNumOfTimesOnlineByTime(String userID, String start, String end) {
        return restTemplate.exchange(API + "/users/" + userID + "/online/" + start + "/" + end + "/online", HttpMethod.GET, null, Integer.class).getBody();
    }

    // get number of times user make a chat with another user during a period of time
    public Integer getNumOfTimesChat(String userID, String start, String end) {
        return restTemplate.exchange(API + "/users/" + userID + "/chat/" + start + "/" + end, HttpMethod.GET, null, Integer.class).getBody();
    }

    public Integer getNumOfTimesChatGroup(String userID, String start, String end) {
        return restTemplate.exchange(API + "/users/" + userID + "/chatgroup/" + start + "/" + end, HttpMethod.GET, null, Integer.class).getBody();
    }

    // 9
    // getNumberOfUserByYear(userList, "2020", "ONLINETIME");

}
