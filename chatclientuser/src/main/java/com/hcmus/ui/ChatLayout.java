package com.hcmus.ui;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatlist.ChatList;
import com.hcmus.ui.searchbar.SearchBar;
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
        JPanel chatList = new JPanel();
        chatList.setLayout(new BorderLayout());
        JScrollPane conversationScrollPane = new ChatList();
        conversationScrollPane.setPreferredSize(new Dimension(200, 400));
        chatList.add(new SearchBar(), BorderLayout.NORTH);
        chatList.add(conversationScrollPane, BorderLayout.CENTER);

        // Message box
        JPanel chatBox = new ChatBox();

        // Add components to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(chatList, BorderLayout.CENTER);
        mainPanel.add(chatBox, BorderLayout.EAST);

        // Add main panel to the frame
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }
}

