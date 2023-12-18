package com.hcmus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.LoginRequest;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class AuthService {
    private static AuthService instance;

    static {
        instance = null;
    }

    private OkHttpClient client;
    private ObjectMapper mapper;

    private AuthService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            return instance = new AuthService();
        }
        return instance;
    }

    public int login(String username, String password) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        LoginRequest loginRequest = new LoginRequest(username, password);
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(loginRequest));
        Request request = new Request.Builder()
                .url("http://localhost:8080/account/login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
//            System.out.println("Auth service: " + response.body().string());
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }

            return (int) apiResponse.getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

