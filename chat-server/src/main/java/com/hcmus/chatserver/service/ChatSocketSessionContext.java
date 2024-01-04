package com.hcmus.chatserver.service;

import com.hcmus.chatserver.entities.user.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatSocketSessionContext implements InitializingBean {
    private final Map<Integer, WebSocketSession> sessions;
    private final Map<String, Integer> session2User;
    private final Map<Integer, List<Integer>> groupChatMembers;
    @Autowired
    private GroupChatService service;

    public ChatSocketSessionContext() {
        sessions = new HashMap<>();
        session2User = new HashMap<>();
        groupChatMembers = new HashMap<>();
    }

    public void sendMsg2User(Integer receiver, TextMessage msg) throws IOException {
        if (sessions.containsKey(receiver)) {
            sessions.get(receiver).sendMessage(msg);
        }
    }

    public void sendMsg2Group(List<Integer> receivers, TextMessage msg) throws IOException {
        for (Integer id : receivers) {
            WebSocketSession session = sessions.getOrDefault(id, null);
            if (session != null) {
                session.sendMessage(msg);
            }
        }
    }

    public void send2Group(Integer groupId, TextMessage msg) throws Exception {
        if (groupChatMembers.containsKey(groupId)) {
            List<Integer> members = groupChatMembers.get(groupId);
            for (Integer id : members) {
                if (sessions.containsKey(id)) {
                    sessions.get(id).sendMessage(msg);
                }
            }
        }
    }

    public void removeSession(String sessionId) {
        Integer userId = session2User.get(sessionId);
        sessions.remove(userId);
        session2User.remove(sessionId);
    }

    public void addSession(Integer userId, WebSocketSession session) {
        sessions.put(userId, session);
        session2User.put(session.getId(), userId);

        // get all chat members
        try {
            List<Integer> groupChatIdsOfUser = service.findAllGroupChatByUserId(userId);
            for (Integer gchatId : groupChatIdsOfUser) {
                List<User> membersId = service.findAllMembers(gchatId);
                List<Integer> integerList = membersId.stream().map(User::getId).collect(Collectors.toList());
                if (!groupChatMembers.containsKey(gchatId)) {
                    groupChatMembers.put(gchatId, integerList);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addGroupChat(int gChatId, List<Integer> membersId) {
        groupChatMembers.put(gChatId, membersId);
    }

    public void updateGroupChatMember(int gChatId) {
        List<Integer> usersId = groupChatMembers.getOrDefault(gChatId, new ArrayList<>());
        try {
            List<User> groupChatIdsOfUser = service.findAllMembers(gChatId);
            usersId = new ArrayList<>(groupChatIdsOfUser.size());
            for (User usr : groupChatIdsOfUser) {
                usersId.add(usr.getId());
            }
            if (!groupChatIdsOfUser.isEmpty()) groupChatMembers.put(gChatId, usersId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Done ChatSession Context");
        service.setContext(this);
    }
}