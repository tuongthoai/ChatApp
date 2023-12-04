package com.hcmus;

import com.hcmus.models.ApiResponse;
import com.hcmus.models.ChatMessage;
import com.hcmus.ui.chatlayout.ChatLayout;
import com.hcmus.ui.loginscreens.Login;

import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame implements Runnable {
    private Login login;
    private ChatLayout chatLayout;

//    private static ObjectMapper mapper = new ObjectMapper();
    // private User user;

    public static void main(String[] args) throws IOException {

        // vio1 du
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/friends/1")
//                .method("GET", null)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();
//
//        if(response.isSuccessful()) {
//            ApiResponse apiResponse = mapper.readValue( response.body().string(), ApiResponse.class);
//            ChatMessage msg = (ChatMessage) apiResponse.getData();
//            System.out.println(mapper.writeValueAsString(apiResponse));
//        }

        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        Login login = new Login();
        login.setVisible(true);

        login.setLoginSucessCallback(new Runnable() {
            @Override
            public void run() {
                chatLayout = new ChatLayout();
                chatLayout.setVisible(true);
                login.dispose();
            }
        });


    }
}