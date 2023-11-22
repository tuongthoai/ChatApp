package com.hcmus.chatserver.entities;

import java.util.Map;

public class SocketMessage {
    private Map<String, Object> headers;
    private String context;

    public SocketMessage(Map<String, Object> headers, String context) {
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
