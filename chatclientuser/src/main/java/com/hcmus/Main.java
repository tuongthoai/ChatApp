package com.hcmus;

import com.hcmus.ui.chatlayout.ChatLayout;
import com.hcmus.ui.loginscreens.Login;

import javax.swing.*;

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