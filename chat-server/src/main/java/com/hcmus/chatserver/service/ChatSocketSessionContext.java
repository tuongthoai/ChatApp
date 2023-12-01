package com.hcmus.chatserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/*
* Error code
* 0: clear safe
* */

@Service
public class ChatSocketSessionContext {
    private ObjectMapper mapper = new ObjectMapper();
    private Map<Integer, WebSocketSession> sessions;
}