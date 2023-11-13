package com.hcmus.chatclientuser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.chatclientuser.entities.MessageEntity;
import com.hcmus.chatclientuser.handler.ChatMsgHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.util.Map;

@Component
public class ClientSession implements InitializingBean {
    private static WebSocketSession session = null;

    {
        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            session = client.execute(new ChatMsgHandler(), new URI(this.socketRegisterPath).toString()).get();
            System.out.println("Client connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${socket.register.path}")
    private String socketRegisterPath;
    private ObjectMapper mapper = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
    }

    public void sendMsg2Server(@NonNull Map<String, Object> header,@NonNull String msg) throws Exception {
        MessageEntity send_msg = new MessageEntity(header, msg);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(send_msg)));
    }
}
