package com.hcmus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.entities.api.ApiResponse;
import com.hcmus.entities.user.User;
import com.hcmus.entities.user.UserActivity;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    public UserService() {
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
                List<User> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<User>>() {});
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

    public List<User> getNewUser(Date startDate, Date endDate) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/new?startDate=" + startDate.toInstant().getEpochSecond() + "&endDate=" + endDate.toInstant().getEpochSecond())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<User> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<User>>() {});
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

    public List<UserActivity> getAllUserActivity() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/activity?startDate=0&endDate=" + new Date().toInstant().getEpochSecond())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<UserActivity> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<UserActivity>>() {});
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

    public List<UserActivity> getUserActivity(Date startDate, Date endDate) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/activity?startDate=" + startDate.toInstant().getEpochSecond() + "&endDate=" + endDate.toInstant().getEpochSecond())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<UserActivity> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<UserActivity>>() {});
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

    public void addUser(User user) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(user));
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/add")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
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

    public void removeUser(int userId) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(userId));
        Request request = new Request.Builder()
                .url("http://localhost:8080/users/remove")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute();) {
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
