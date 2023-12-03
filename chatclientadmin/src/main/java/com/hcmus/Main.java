package com.hcmus;

import com.hcmus.ui.screens.MainScreen;

import javax.swing.*;

public class Main extends JFrame implements Runnable {
    private MainScreen mainScreen;
    // private User user;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        mainScreen = new MainScreen();
        mainScreen.setVisible(true);
    }
}