package com.hcmus.chatserver.entities.messages;

import java.util.Map;

public class CustomMessage {
    private Map<String, Object> headers;
    private String context;

    public CustomMessage() {
    }

    public CustomMessage(Map<String, Object> headers, String context) {
        this.headers = headers;
        this.context = context;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
