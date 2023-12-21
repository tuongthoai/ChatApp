package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.entities.api.ApiResponse;
import com.hcmus.entities.loginhistory.LoginHistory;
import com.hcmus.entities.loginhistory.UserLoginTime;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginHistoryService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    public LoginHistoryService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }
    public List<LoginHistory> getUserLoginHistory(int userId) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/loginhistory/" + userId)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();


        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<LoginHistory> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<LoginHistory>>() {});
                }
                return data;
            }
            else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserLoginTime> getUserLoginTime() {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/loginhistory/users")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<UserLoginTime> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<UserLoginTime>>() {
                    });
                }
                return data;
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
