package com.hcmus.chatserver.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class MessageEntity {
    private Map<String, Object> headers;
    private String context;
}
