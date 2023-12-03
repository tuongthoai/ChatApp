package com.hcmus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ApiResponse;
import com.hcmus.socket.SocketSession;
import com.hcmus.ui.chatlayout.ChatLayout;
import com.hcmus.ui.loginscreens.Login;
import okhttp3.*;

import javax.swing.*;
import java.io.DataInput;
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
//            System.out.println(mapper.writeValueAsString(apiResponse));
//        }

        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        Login login = new Login();
        login.setVisible(true);

        // client socket handler here.....
        SocketSession session = new SocketSession();

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