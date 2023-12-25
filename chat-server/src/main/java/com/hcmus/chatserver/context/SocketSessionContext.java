package com.hcmus.chatserver.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.messages.ClientChatMessage;
import com.hcmus.chatserver.service.ChatSocketSessionContext;
import com.hcmus.chatserver.service.GroupChatService;
import com.hcmus.chatserver.service.UserService;
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
    private final ChatSocketSessionContext context;
    private final GroupChatService groupChatService;
    private final UserService userService;
    private final ObjectMapper mapper;

    public SocketSessionContext(ChatSocketSessionContext context, GroupChatService groupChatService, UserService userService) {
        this.context = context;
        this.groupChatService = groupChatService;
        this.userService = userService;
        mapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        List<String> ids = headers.get("user_send_id");
        int userId = 0;
        if (ids != null) {
            userId = Integer.valueOf(ids.get(0));
            context.addSession(userId, session);
            try {
                userService.updateUserStatus(userId, true);
                userService.setLoginTime(userId);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ClientChatMessage msg = mapper.readValue(payload, ClientChatMessage.class);
        if(msg.getMsgType() == null) {
            context.send2Group(msg.getGroupChatId(), message);
            groupChatService.persistMsg(msg);
        } else {
            if (msg.getMsgType().equals("SYS")) {
                context.send2Group(msg.getGroupChatId(), message);
                context.updateGroupChatMember(msg.getGroupChatId());
            }
        }
        System.out.println(payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        System.out.println("A user has terminated");
        context.removeSession(session.getId());
        HttpHeaders headers = session.getHandshakeHeaders();
        List<String> ids = headers.get("user_send_id");
        int userId = 0;
        if (ids != null) {
            userId = Integer.valueOf(ids.get(0));
            context.addSession(userId, session);
            try {
                userService.updateUserStatus(userId, false);
                userService.setDisconnectTime(userId);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
    }
}
