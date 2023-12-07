package com.hcmus.chatserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatSocketSessionContext {
    private final Map<Integer, WebSocketSession> sessions;
    private final Map<String, Integer> session2User;
    private final Map<Integer, List<Integer>> groupCharMembers;

    public ChatSocketSessionContext() {
        sessions = new HashMap<>();
        session2User = new HashMap<>();
        groupCharMembers = new HashMap<>();
    }

    public void sendMsg2User(Integer receiver, TextMessage msg) throws IOException {
        WebSocketSession session = sessions.getOrDefault(receiver, null);
        if (session != null) {
            session.sendMessage(msg);
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

    public void removeSession(String sessionId) {
        Integer userId = session2User.get(sessionId);
        sessions.remove(userId);
        session2User.remove(sessionId);
    }

    public void addSession(Integer userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void addGroupChat(int gChatId, List<Integer> membersId) {
        groupCharMembers.put(gChatId, membersId);
    }
}