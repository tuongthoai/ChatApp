package com.hcmus.chatclientuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@SpringBootApplication
public class ChatclientuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatclientuserApplication.class, args);

		WebSocketSession session = null;
		try {
			StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
			session = webSocketClient.doHandshake(new TextWebSocketHandler() {
				@Override
				public void handleTextMessage(WebSocketSession session, TextMessage message) {
					System.out.println("Received message: " + message.getPayload());
				}
			}, new URI("ws://localhost:8080/chat").toString()).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (session != null) {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.print("Enter message: ");
				String message = scanner.nextLine();
				try {
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class MySessionHandler extends StompSessionHandlerAdapter {
		@Override
		public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
			session.subscribe("/topic/public", this);
			session.send("/app/sendMessage", "Testing message from client");
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			System.out.println("Received: " + payload.toString());
		}
	}
}
