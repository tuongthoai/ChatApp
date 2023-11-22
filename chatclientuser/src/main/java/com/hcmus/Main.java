package com.hcmus;

import com.hcmus.socket.ClientSocketHandler;
import com.hcmus.ui.ChatLayout;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        SwingUtilities.invokeLater(() -> {
            new ChatLayout().setVisible(true);
        });
    }
}