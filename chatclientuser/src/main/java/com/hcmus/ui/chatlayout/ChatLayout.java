package com.hcmus.ui.chatlayout;

import com.hcmus.ChatHashMap;
import com.hcmus.UserProfile;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.datatest.DataTest;
import com.hcmus.ui.friendscreen.FriendHomePage;
import com.hcmus.ui.sidebar.Sidebar;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ChatLayout extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private ChatContext chatContext;

    public ChatLayout() throws URISyntaxException {
        setTitle("Chat UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        this.chatContext = ChatContext.getInstance();

        // Sidebar
        Sidebar sidebar = new Sidebar(contentPanel, cardLayout);
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Main screen
        ChatScreen mainScreen = new ChatScreen(this.chatContext);
        contentPanel.add(mainScreen, "CHAT");

        // Friend screen
        DataTest data = new DataTest();
        FriendHomePage friendHomePage = new FriendHomePage(data, cardLayout, contentPanel);
        contentPanel.add(friendHomePage, "FRIENDS");

        // Settings screen
        SettingsScreen settingsScreen = new SettingsScreen();
        contentPanel.add(settingsScreen, "SETTINGS");

        cardLayout.show(contentPanel, "CHAT");

//        // Main panel with BorderLayout
//        JPanel mainPanel = new JPanel(new BorderLayout());
//
//        // Sidebar with GridBagLayout
//        JPanel sidebarPanel = new Sidebar();
//
//        // List of conversations (you can use JList or other components)
//        JPanel chatList = new JPanel();
//        chatList.setLayout(new BorderLayout());
//        JScrollPane conversationScrollPane = new ChatList();
//        conversationScrollPane.setPreferredSize(new Dimension(200, 400));
//        chatList.add(new SearchBar(), BorderLayout.NORTH);
//        chatList.add(conversationScrollPane, BorderLayout.CENTER);
//
//        // Message box
//        JPanel chatBox = new ChatBox();
//
//        // Add components to main panel
//        mainPanel.add(sidebarPanel, BorderLayout.WEST);
//        mainPanel.add(chatList, BorderLayout.CENTER);
//        mainPanel.add(chatBox, BorderLayout.EAST);

        // Add main panel to the frame
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public void reloadChatScreen() {

    }
}

