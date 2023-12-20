package com.hcmus.chatserver.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.messages.ClientChatMessage;
import com.hcmus.chatserver.service.ChatSocketSessionContext;
import com.hcmus.chatserver.service.GroupChatService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.List;
@Service
public class SocketSessionContext extends TextWebSocketHandler implements InitializingBean {
    @Autowired
    private GroupChatService groupChatService;
    private final ChatSocketSessionContext context;
    private ObjectMapper mapper = new ObjectMapper();

    public SocketSessionContext(ChatSocketSessionContext context) {
        this.context = context;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        List<String> ids = headers.get("user_send_id");
        if (ids != null) {
            context.addSession(Integer.valueOf(ids.get(0)), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ClientChatMessage msg = mapper.readValue(payload, ClientChatMessage.class);
        context.send2Group(msg.getGroupChatId(), message);
        groupChatService.persistMsg(msg);
        System.out.println(payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        System.out.println("A user has terminated");
        context.removeSession(session.getId());
    }

    @Override
    public void afterPropertiesSet() {
    }
}
