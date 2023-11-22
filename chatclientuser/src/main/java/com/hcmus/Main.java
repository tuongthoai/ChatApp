package com.hcmus;

import com.hcmus.socket.ClientSocketHandler;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        ClientSocketHandler client = new ClientSocketHandler(new URI("ws://localhost:8080/chat"));
        client.connect();
        System.out.println("Hello world!");
    }
}