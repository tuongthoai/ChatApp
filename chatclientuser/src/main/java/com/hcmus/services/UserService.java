package com.hcmus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.utils.Link;
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
        Request request = new Request.Builder().url(Link.getLink("service") + "users/" + userId).method("GET", null).addHeader("Content-Type", "application/json").build();

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
        Request request = new Request.Builder().url(Link.getLink("service") + "users/" + userId + "/countFriends").method("GET", null).addHeader("Content-Type", "application/json").build();

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
        Request request = new Request.Builder().url(Link.getLink("service") + "users/update").method("PUT", body).addHeader("Content-Type", "application/json").build();

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
        Request request = new Request.Builder().url(Link.getLink("service") + "friends/" + userId).method("GET", null).addHeader("Content-Type", "application/json").build();

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

    public List<User> findAllStrangers(int userId) throws Exception {
        List<User> result = new ArrayList<>();
        Request request = new Request.Builder().url(Link.getLink("service") + "friends/" + userId + "/stranger").method("GET", null).addHeader("Content-Type", "application/json").build();
        try (Response response = client.newCall(request).execute()) {
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new IOException("Request failed: " + response.code());
            }
            result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {
            });
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllFriendNotInGroup(int userId, int groupId) throws Exception {
        List<User> result = new ArrayList<>();
        Request request = new Request.Builder().url(Link.getLink("service") + "friends/" + userId + "/exceptGroup/" + groupId).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new Exception(apiResponse.getErrorReason());
            }
            result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // check if user1 is blocked by user2 or vice versa
    public boolean isBlockedBy(int user1, int user2) throws Exception {
        boolean result = false;
        Request request = new Request.Builder().url(Link.getLink("service") + "users/" + "isBlockedBy/" + user1 + "/" + user2).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new Exception(apiResponse.getErrorReason());
            }
            result = mapper.convertValue(apiResponse.getData(), Boolean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void blockUser(int user1, int user2) throws Exception {
        Request request = new Request.Builder().url(Link.getLink("service") + "users/" + "block/" + user1 + "/" + user2).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body().toString().contains("Can't block user")) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void unblockUser(int user1, int user2) throws Exception {
        Request request = new Request.Builder().url(Link.getLink("service") + "users/" + "unblock/" + user1 + "/" + user2).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body().toString().contains("Can't block user")) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllBlockedUsers(int userId) throws Exception {
        List<User> result = new ArrayList<>();
        Request request = new Request.Builder().url(Link.getLink("service") + "users/" + "blockedUsers/" + userId).method("GET", null).addHeader("Content-Type", "application/json").build();

        try {
            Response response = client.newCall(request).execute();
            ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
            if (apiResponse.isError()) {
                throw new Exception(apiResponse.getErrorReason());
            }
            result = mapper.convertValue(apiResponse.getData(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
