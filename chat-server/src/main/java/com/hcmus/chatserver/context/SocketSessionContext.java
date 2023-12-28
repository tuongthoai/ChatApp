package com.hcmus.chatserver.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatserver.entities.messages.ClientChatMessage;
import com.hcmus.chatserver.repository.UserRepository;
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
            System.out.printf("user %d has logged in\n", userId);
            context.addSession(userId, session);
            try {
                userService.updateUserStatus(userId, true);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ClientChatMessage msg = mapper.readValue(payload, ClientChatMessage.class);
        // check if current user is blocked by admin
        if (userService.isUserBlocked(msg.getUserSentId())) {
            TextMessage response = new TextMessage("%% <You are blocked> %%");
            System.out.println("You are blocked");
            session.sendMessage(response);
            return;
        }
        // check if the receiver is blocked by current user
        int receiverId = -1;
        if ((receiverId = groupChatService.getOtherMemberId(msg.getGroupChatId(), msg.getUserSentId())) != -1) {
            if (userService.isBlockedBy(msg.getUserSentId(), receiverId)) {
                TextMessage response = new TextMessage("%% <This private chat is disabled> %%");
                System.out.println("This private chat is disabled");
                session.sendMessage(response);
                return;
            }
        }

        if(msg.getMsgType() == null) {
            context.send2Group(msg.getGroupChatId(), message);
            groupChatService.persistMsg(msg);
        } else {
            if (msg.getMsgType().equals("SYS")) {
                context.send2Group(msg.getGroupChatId(), message);
                context.updateGroupChatMember(msg.getGroupChatId());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        context.removeSession(session.getId());
        HttpHeaders headers = session.getHandshakeHeaders();
        List<String> ids = headers.get("user_send_id");
        int userId = 0;
        if (ids != null) {
            userId = Integer.valueOf(ids.get(0));
            System.out.printf("user %d has terminated\n", userId);
            context.addSession(userId, session);
            try {
                userService.updateUserStatus(userId, false);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
    }
}
