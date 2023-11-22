package com.hcmus.models;

import java.util.HashMap;

public class ChatMessage {
    private HashMap<String, Object> headers;
    private String content;

    public ChatMessage() {
        headers = new HashMap<>();
        content = null;
    }

    public ChatMessage(HashMap<String, Object> headers, String content) {
        this.headers = headers;
        this.content = content;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, Object> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
