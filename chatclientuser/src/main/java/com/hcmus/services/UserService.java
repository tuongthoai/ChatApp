package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.User;
import okhttp3.*;

import java.io.IOException;

public class UserService {
    private static UserService instance;

    static {
        instance = null;
    }

    private OkHttpClient client;
    private ObjectMapper mapper;

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
}
