package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.utils.Link;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.User;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        Request request = new Request.Builder().url("http://localhost:8080/friends/" + userID + "/unfriend/" + friendID).method("DELETE", body).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAllStrangers(int userId) throws Exception {
        List<User> result = new ArrayList<>();
        Request request = new Request.Builder().url(Link.getLink("service") + "friends/" + userId + "/stranger").method("GET", null).addHeader("Content-Type", "application/json").build();
        try (Response response = client.newCall(request).execute()) {
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }
            result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {
            });
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addfriend(int userID, int friendID) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");

        Request request = new Request.Builder().url(Link.getLink("service") + "friends/" + userID + "/addfriend/" + friendID).method("POST", body).addHeader("Content-Type", "application/json").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() != 200) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            response.close();
        }
    }

}
