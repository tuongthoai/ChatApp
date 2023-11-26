package com.hcmus.ui.chatlayout;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatlist.ChatList;
import com.hcmus.ui.searchbar.SearchBar;

import javax.swing.*;
import java.awt.*;

public class ChatScreen extends JPanel {
    public ChatScreen() {
        setLayout(new BorderLayout());

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

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
        mainPanel.add(chatList, BorderLayout.WEST);
        mainPanel.add(chatBox, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }
}
