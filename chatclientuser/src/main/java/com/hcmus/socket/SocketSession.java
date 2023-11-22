package com.hcmus.socket;


import java.net.URI;

public class SocketSession {
    private ClientSocketHandler handler;

    public SocketSession() {
        try {
            handler = new ClientSocketHandler(new URI("ws://localhost:8080/chat"));
            handler.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
