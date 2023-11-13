package com.hcmus.chatclientuser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatclientuser.handler.ChatMsgHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

@Component
public class ClientSession implements InitializingBean {
    static {
        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            session = client.execute(new ChatMsgHandler(), new URI("ws://localhost:8080/chat").toString()).get();
            System.out.println("Client connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${socket.register.path}")
    private String socketRegisterPath;
    private static WebSocketSession session = null;
    private ObjectMapper mapper = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }

    public void sendMsg() throws Exception {

    }
}
