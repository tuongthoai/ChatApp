package com.hcmus.chatclientuser.repository;

import com.hcmus.chatclientuser.entities.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClientLocalStorage {
    private final int DEFAULT_NUMBER_OF_MSG_LOAD = 32;
    private final Map<String, List<MessageEntity>> localMessageStorage;

    public ClientLocalStorage() {
        localMessageStorage = new HashMap<>();
    }
}
