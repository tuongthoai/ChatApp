package com.hcmus.chatclientuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@SpringBootApplication
public class ChatclientuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatclientuserApplication.class, args);
    }
}
