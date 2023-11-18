package com.hcmus.chatclientuser.services;

import com.hcmus.chatclientuser.session.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private ClientSession session;

    public void handle() throws Exception {
        session.sendMsg2Server(new HashMap<>(), "helloworld");

        Map<String, Object> header = new HashMap<>();
        String msg = " Hello world";
        session.sendMsg2Server(header, msg);
    }
}


