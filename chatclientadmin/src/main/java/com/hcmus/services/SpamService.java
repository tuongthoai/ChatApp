package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.entities.api.ApiResponse;
import com.hcmus.entities.spam.SpamReport;
import com.hcmus.entities.user.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpamService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    public SpamService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public List<SpamReport> getAllSpamReports() {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost:8080/spam/all")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();


        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                List<SpamReport> data = new ArrayList<>();
                if (!apiResponse.isError()) {
                    data = mapper.readValue(mapper.writeValueAsString(apiResponse.getData()), new TypeReference<List<SpamReport>>() {});
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
}
