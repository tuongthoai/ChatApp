package com.hcmus.models;

import java.util.HashMap;
import java.util.Map;

public class ChatMessage {
    private Map<String, String> headers;
    private String content;

    public ChatMessage() {
        headers = new HashMap<>();
        content = null;
    }

    public ChatMessage(Map<String, String> headers, String content) {
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
