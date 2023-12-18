package com.hcmus.ui.chatlayout;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatlist.ChatList;
import com.hcmus.ui.searchbar.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;

public class ChatScreen extends JPanel {
    JPanel chatBoxContainer;

    public ChatScreen() throws URISyntaxException {
        setLayout(new BorderLayout());

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // List of conversations (you can use JList or other components)
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BorderLayout());
        JPanel conversationScrollPane = new ChatList(this);
        conversationScrollPane.setPreferredSize(new Dimension(220, 400));
        chatListPanel.add(new SearchBar(), BorderLayout.NORTH);
        chatListPanel.add(conversationScrollPane, BorderLayout.CENTER);
        chatListPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Message box
        chatBoxContainer = new JPanel();
        chatBoxContainer.setLayout(new BorderLayout());
        chatBoxContainer.add(new ChatBox(), BorderLayout.CENTER);

        // Add components to main panel
        mainPanel.add(chatListPanel, BorderLayout.WEST);
        mainPanel.add(chatBoxContainer, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    public void updateChatBox(ChatBox newChatBox) {
        chatBoxContainer.removeAll();
        chatBoxContainer.add(newChatBox, BorderLayout.CENTER);
        chatBoxContainer.revalidate();
        chatBoxContainer.repaint();
    }
}
