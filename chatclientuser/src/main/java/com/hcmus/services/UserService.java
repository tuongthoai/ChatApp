package com.hcmus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.User;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService instance;

    static {
        instance = null;
    }

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public UserService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
            return instance;
        }
        return instance;
    }

    public User getUserById(int userId) throws Exception {
        User user = null;
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder().url("http://localhost:8080/users/" + userId).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }

            user = mapper.convertValue(apiResponse.getData(), new TypeReference<User>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public long countFriends(int userId) throws Exception {
        Long result = 0L;
        Request request = new Request.Builder().url("http://localhost:8080/users/" + userId + "/countFriends").method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }

            result = mapper.convertValue(apiResponse.getData(), Long.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void updateUser(User user) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(user));
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/update")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body().toString().contains("Can't update user")) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAllFriends(int userId) throws Exception {
        List<User> result = new ArrayList<>();
        Request request = new Request.Builder().url("http://localhost:8080/friends/" + userId).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }

            result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
