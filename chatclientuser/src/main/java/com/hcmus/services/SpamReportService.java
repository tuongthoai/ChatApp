package com.hcmus.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.utils.Link;
import com.hcmus.models.ApiResponse;
import com.hcmus.models.SpamReportRequest;
import okhttp3.*;

import java.io.IOException;

public class SpamReportService {
    private static SpamReportService instance;

    static {
        instance = null;
    }

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    private SpamReportService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();
    }

    public static SpamReportService getInstance() {
        if (instance == null) {
            instance = new SpamReportService();
        }
        return instance;
    }

    public boolean submitReport(int userId, int reportedUserId, String content) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        SpamReportRequest reportRequest = new SpamReportRequest(userId, reportedUserId, content);
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(reportRequest));
        Request request = new Request.Builder().url(Link.getLink("service") + "spam/create").method("POST", body).addHeader("Content-Type", "application/json").build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
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
