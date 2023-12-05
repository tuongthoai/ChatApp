package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.User;
import okhttp3.*;

import java.io.IOException;

public class UserService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    public UserService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public User getUserById(int userId) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/" + userId)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }

            User user = mapper.convertValue(apiResponse.getData(), new TypeReference<User>() {});

            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
