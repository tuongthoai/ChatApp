package com.hcmus.utils;

public class Link {
    public final static String getLink(String type) {
        switch (type.trim().toLowerCase()) {
            case "image":
                return "./chatclientuser/src/main/java/com/hcmus/ui/images/";
            case "service":
                return "http://localhost:8080/";
            default:
                return "";
        }
    }
}