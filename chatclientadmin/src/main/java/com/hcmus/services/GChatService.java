package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.Link;
import com.hcmus.entities.api.ApiResponse;
import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.user.User;
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

    public List<GroupChat> getAllGChats() {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(Link.getLink("service") + "gchats/all")
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

    public List<User> getAllMembers(Integer groupId) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(Link.getLink("service") + "gchats/members/" + groupId)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<User> data = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {});
                return data;
            }
            else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllAdmins(Integer groupId) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(Link.getLink("service") + "gchats/admins/" + groupId)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<User> data = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {});
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
