package com.hcmus;

import com.hcmus.ui.ChatLayout;
import com.hcmus.ui.Login;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Main extends JFrame implements Runnable {
    private Login login;
    private ChatLayout chatLayout;
    // private User user;

    public static void main(String[] args) {
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

        // client socket handler here.....

    }
}