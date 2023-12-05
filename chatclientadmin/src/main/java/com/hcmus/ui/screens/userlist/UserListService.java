package com.hcmus.ui.screens.userlist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

    public List<User> getAllUsers() {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/all")
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

    public void addUser(User user) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(user));
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/add")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            // ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class); --> LOI O DAY
            if (response.body().toString().contains("Can't add user")) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUser(int userId) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(userId));
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/remove")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
            System.out.println("User list service: " + response.body().string());
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
//                if (apiResponse.isError()) {
//                    throw new IOException("Request failed: " + response.code());
//                }
                if (response.body().toString().contains("Can't remove user")) {
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

    public void updateUser(User user) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(user));
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/update")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body().toString().contains("Can't update user")) {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void adminBlockUser(int userId) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/adminBlock/" + userId)
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
            System.out.println("User list service: " + response.body().string());
            if (response.isSuccessful()) {
                if (response.body().toString().contains("Can't block user")) {
                    throw new IOException("Request failed: " + response.code());
                }
            } else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
