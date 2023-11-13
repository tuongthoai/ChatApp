package com.hcmus.chatclientuser.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MessageEntity {
    private Map<String, Object> headers;
    private String context;
}
