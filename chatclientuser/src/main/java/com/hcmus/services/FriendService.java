package com.hcmus.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class FriendService {
    private static FriendService instance;

    static {
        instance = null;
    }

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public FriendService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public static FriendService getInstance() {
        if (instance == null) {
            instance = new FriendService();
            return instance;
        }
        return instance;
    }

    public void unfriend(int userID, int friendID) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");

        Request request = new Request.Builder()
                .url("http://localhost:8080/friends/" + userID + "/unfriend/" + friendID)
                .method("DELETE", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
