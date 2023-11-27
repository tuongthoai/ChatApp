package com.hcmus.ui.chatlayout;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatbox.ChatMessage;
import com.hcmus.ui.chatlist.ChatList;
import com.hcmus.ui.searchbar.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatScreen extends JPanel {
    ChatBox chatBox;
    public ChatScreen() {
        setLayout(new BorderLayout());

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // List of conversations (you can use JList or other components)
        JPanel chatList = new JPanel();
        chatList.setLayout(new BorderLayout());
        JPanel conversationScrollPane = new ChatList(this);
        conversationScrollPane.setPreferredSize(new Dimension(220, 400));
        chatList.add(new SearchBar(), BorderLayout.NORTH);
        chatList.add(conversationScrollPane, BorderLayout.CENTER);
        chatList.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Message box
        chatBox = new ChatBox();

        // Add components to main panel
        mainPanel.add(chatList, BorderLayout.WEST);
        mainPanel.add(chatBox, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    public void updateChatBox(ArrayList<ChatMessage> chatMessages) {
        chatBox.setChatMessages(chatMessages);
        chatBox.displayChatMessage();
    }
}
