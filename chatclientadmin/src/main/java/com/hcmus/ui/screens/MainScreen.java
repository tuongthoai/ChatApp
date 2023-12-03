package com.hcmus.ui.screens;

import com.hcmus.ui.sidebar.Sidebar;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainScreen() {
        setTitle("Chat management system");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Sidebar
        Sidebar sidebar = new Sidebar(contentPanel, cardLayout);
        mainPanel.add(sidebar, BorderLayout.WEST);

        // User list
        UserList userList = new UserList();
        contentPanel.add(userList, "USER_LIST");

        // Chat list
        ChatList chatList = new ChatList();
        contentPanel.add(chatList, "CHAT_LIST");

        // Login history
        LoginHistory loginHistory = new LoginHistory();
        contentPanel.add(loginHistory, "LOGIN_HISTORY");

        // New user
        NewUsers newUser = new NewUsers();
        contentPanel.add(newUser, "NEW_USER");

        // Active users chart
        OnlChart onlChart = new OnlChart();
        contentPanel.add(onlChart, "ONL_CHART");

        // Online users
        OnlineUsers onlineUsers = new OnlineUsers();
        contentPanel.add(onlineUsers, "ONLINE_USERS");

        // Registration chart
        RegChart regChart = new RegChart();
        contentPanel.add(regChart, "REG_CHART");

        // Spam List
        SpamList spamList = new SpamList();
        contentPanel.add(spamList, "SPAM_LIST");

        // Users' friends
        UsersFriends usersFriends = new UsersFriends();
        contentPanel.add(usersFriends, "USERS_FRIENDS");

        cardLayout.show(contentPanel, "USER_LIST");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }
}
