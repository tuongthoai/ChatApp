package com.hcmus.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ChatMessage;
import com.hcmus.observer.Subscribe;
import com.sun.security.jgss.GSSUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ChatContext extends WebSocketClient {
    private final Map<Integer, Subscribe> subscribersMap = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public ChatContext(URI serverUri) {
        super(serverUri);
    }

    public ChatContext(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public void addObserver(Subscribe subscriber) {
        subscribersMap.put(subscriber.getObserverId(), subscriber);
    }

    public void removeObserver(Subscribe subscriber) {
        subscribersMap.remove(subscriber.getObserverId());
    }

    public void notify(Integer id, Object obj) {
        (subscribersMap.get(id)).update(obj);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connection Opened");
    }

    @Override
    public void onMessage(String message) {
        ChatMessage msg = null;
        try {
            msg = mapper.readValue(message, ChatMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Network error");
        }

        if (msg != null) {
            this.notify((Integer) msg.getHeaders().get("GCHATID"), msg);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection close");
    }

    @Override
    public void onError(Exception ex) {

    }
}
