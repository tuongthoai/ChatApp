package com.hcmus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.Link;
import com.hcmus.entities.api.ApiResponse;
import com.hcmus.entities.user.User;
import com.hcmus.ui.table.UnixTimestampConverter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegChartService {
    private OkHttpClient client;
    private ObjectMapper mapper;
    private List<Long> createdTime;
    public RegChartService() {
        client = new OkHttpClient().newBuilder().build();
        mapper = new ObjectMapper();

        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(Link.getLink("service") + "users/createdTime")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();


        try (Response response = client.newCall(request).execute();) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = mapper.readValue(response.body().string(), ApiResponse.class);
                createdTime = mapper.convertValue(apiResponse.getData(), new TypeReference<List<Long>>() {});
            }
            else {
                throw new IOException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Integer> getYears(){
        List<Integer> res = new ArrayList<>();
        for(long time : this.createdTime){
            LocalDateTime convertedTime = UnixTimestampConverter.unix2DateTime(time);
            if(!res.contains(convertedTime.getYear()))
                res.add(convertedTime.getYear());
        }
        return res;
    }
    public int[] getNumberOfRegisterUser(int year){
        int[] numberOfUser = new int[12];
        Arrays.fill(numberOfUser, 0, 12, 0);

        for(long createdTime : this.createdTime){
            LocalDateTime convertedTime = UnixTimestampConverter.unix2DateTime(createdTime);
            if(convertedTime.getYear() == year){
                int month = convertedTime.getMonthValue();
                numberOfUser[month - 1]++;
            }
        }

        return numberOfUser;
    }
}
