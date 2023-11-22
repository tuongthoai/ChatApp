package com.hcmus.ui;

import com.hcmus.ui.sidebar.Sidebar;

import javax.swing.*;
import java.awt.*;

public class ChatLayout extends JFrame {
    public ChatLayout() {
        setTitle("Chat UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar with GridBagLayout
        JPanel sidebarPanel = new Sidebar();

        // List of conversations (you can use JList or other components)
        JList<String> conversationList = new JList<>(new String[]{"Conversation 1", "Conversation 2"});
        JScrollPane conversationScrollPane = new JScrollPane(conversationList);
        conversationScrollPane.setPreferredSize(new Dimension(200, 400));

        // Message box
        JTextArea messageBox = new JTextArea();
        JScrollPane messageScrollPane = new JScrollPane(messageBox);
        messageScrollPane.setPreferredSize(new Dimension(500, 400));

        // Add components to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(conversationScrollPane, BorderLayout.CENTER);
        mainPanel.add(messageScrollPane, BorderLayout.EAST);

        // Add main panel to the frame
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }
}

