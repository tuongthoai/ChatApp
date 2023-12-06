package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.GroupChat;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class GChatService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    public GChatService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public List<GroupChat> getGChatList(int userId) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/gchats/" + userId)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<GroupChat> data = mapper.convertValue(apiResponse.getData(), new TypeReference<List<GroupChat>>() {});
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
