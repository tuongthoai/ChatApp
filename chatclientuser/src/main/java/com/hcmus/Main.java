package com.hcmus;

import com.hcmus.ui.chatlayout.ChatLayout;
import com.hcmus.ui.loginscreens.Login;

import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame implements Runnable {
    private ChatLayout chatLayout;

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        Login login = new Login();
        login.setVisible(true);
    }
}