package com.hcmus.chatserver.entities.messages;

import java.util.Map;

public class CustomMessage {
    private Map<String, String> headers;
    private String content;

    public CustomMessage() {
    }

    public CustomMessage(Map<String, String> headers, String content) {
        this.headers = headers;
        this.content = content;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
