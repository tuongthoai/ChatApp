package com.hcmus.socket;

import javax.management.RuntimeErrorException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class SocketSession {
    private ClientSocketHandler handler;

    public SocketSession() {
        try {
            handler = new ClientSocketHandler(new URI("ws://localhost:8080/chat"));
            handler.connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
