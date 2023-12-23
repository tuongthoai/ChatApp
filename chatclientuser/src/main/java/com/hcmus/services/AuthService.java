package com.hcmus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.Link;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.LoginRequest;
import com.hcmus.models.RegisterRequest;
import okhttp3.*;

import java.io.IOException;
import java.sql.SQLOutput;

public class AuthService {
    private static AuthService instance;

    static {
        instance = null;
    }

    private final OkHttpClient client;
    private final ObjectMapper mapper;

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
        Request request = new Request.Builder().url(Link.getLink("service") + "account/login").method("POST", body).addHeader("Content-Type", "application/json").build();

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

    public long lastLogin(int userId) throws Exception {
        Long result = 0L;
        Request request = new Request.Builder().url(Link.getLink("service") + "loginhistory/" + userId + "/last").method("GET", null).addHeader("Content-Type", "application/json").build();

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

    public int register(String username, String password, String email) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
        Request request = new Request.Builder()
                .url(Link.getLink("service") + "account/register")
                .method("POST", RequestBody.create(mediaType, mapper.writeValueAsString(registerRequest)))
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                if (apiResponse.isError()) {
                    throw new IOException("Request failed: " + response.code());
                }
                return (int) apiResponse.getData();
            }
            else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void forgotPassword(String email) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(Link.getLink("service") + "account/forgot")
                .method("POST", RequestBody.create(mediaType, email))
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                if (apiResponse.isError()) {
                    throw new IOException("Request failed: " + response.code());
                }
            }
            else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

