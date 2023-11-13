package com.hcmus.chatclientuser.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class ChatAppMsg {
    private Map<String, String> headers;
    private String context;
}
