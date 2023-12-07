package com.hcmus.chatserver.context;

import com.hcmus.chatserver.service.ChatSocketSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SocketSessionContext extends TextWebSocketHandler {
    @Autowired
    private ChatSocketSessionContext context;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        HttpHeaders headers = session.getHandshakeHeaders();
        Map<String, Object> atttributes = session.getAttributes();
//        sessions.add(session);
        context.addSession(Integer.valueOf((String) atttributes.get("user_send_id")), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        System.out.println("A user has terminated");
        sessions.remove(session);
    }
}
