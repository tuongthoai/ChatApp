package com.hcmus;

public class Link {
    public final static String getLink(String type) {
        switch (type.trim().toLowerCase()) {
            case "image":
                return System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
            case "service":
                return "http://localhost:8080/";
            default:
                return "";
        }
    }
}