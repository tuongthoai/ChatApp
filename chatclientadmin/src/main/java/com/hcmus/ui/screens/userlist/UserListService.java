package com.hcmus.ui.screens.userlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.entities.api.ApiResponse;
import com.hcmus.entities.user.User;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class UserListService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    public UserListService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    List<User> getAllUsers() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/all")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();


        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<User> data = mapper.convertValue(apiResponse.getData(), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
//
                return data;
            }
            else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
