package com.hcmus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GChatService {
    private static GChatService instance;

    static {
        instance = null;
    }

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    private GChatService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public static GChatService getInstance() {
        if (instance == null) {
            return instance = new GChatService();
        }
        return instance;
    }

    public List<GroupChat> getGChatList(int userId) {
        Request request = new Request.Builder().url("http://localhost:8080/gchats/" + userId).method("GET", null).addHeader("Content-Type", "application/json").build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<GroupChat> data = mapper.convertValue(apiResponse.getData(), new TypeReference<List<GroupChat>>() {
                });
                return data;
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GroupChatMember> getGroupChatMembers(int gchatId) {
        List<GroupChatMember> result = new ArrayList<>();
        try {
            Request request = new Request.Builder().url("http://localhost:8080/gchats/members/show/" + gchatId).method("GET", null).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<GroupChatMember>>() {
                });
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return result;
    }

    public Long countNoGroupChat(int userId) throws Exception {
        Long result = 0L;
        try {
            Request request = new Request.Builder().url("http://localhost:8080/gchats/" + userId + "/count").method("GET", null).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                result = mapper.convertValue(apiResponse.getData(), Long.class);
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return result;
    }

    public List<ChatContentDTO> getAllHistory(int groupId) {
        List<ChatContentDTO> result = new ArrayList<>();
        try {
            Request request = new Request.Builder().url("http://localhost:8080/gchats/" + groupId + "/messages").method("GET", null).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<ChatContentDTO>>() {
                });
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return result;
    }

    public boolean renameGroupChat(int groupId, String newName) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        RenameGroupRequest renameGroupRequest = new RenameGroupRequest(groupId, newName);
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(renameGroupRequest));
        Request request = new Request.Builder().url("http://localhost:8080/gchats/rename").method("POST", body).addHeader("Content-Type", "application/json").build();
        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                if (apiResponse.isError()) {
                    throw new IOException("Request failed: " + response.code());
                }
                return (Boolean) apiResponse.getData();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
