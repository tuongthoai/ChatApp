package com.hcmus.ui.chatlayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmus.observer.Subscriber;
import com.hcmus.services.AuthService;
import com.hcmus.services.ComponentIdContext;
import com.hcmus.services.EventHandlerService;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.datatest.DataTest;
import com.hcmus.ui.friendscreen.FriendHomePage;
import com.hcmus.ui.sidebar.Sidebar;
import com.hcmus.utils.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ChatLayout extends JFrame implements Subscriber {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private ChatContext chatContext;

    public ChatLayout() throws URISyntaxException, SQLException {
        setTitle("Chat UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    AuthService.getInstance().setDCTime(UserProfile.getUserProfile().getId());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                // close all frames
                System.exit(0);
            }
        });
        setPreferredSize(new Dimension(1000, 600));

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
        EventHandlerService.getInstance().addObserver(mainScreen);
        contentPanel.add(mainScreen, "CHAT");

        // Friend screen
        FriendHomePage friendHomePage = new FriendHomePage(cardLayout, contentPanel);
        contentPanel.add(friendHomePage, "FRIENDS");

        // Settings screen
        SettingsScreen settingsScreen = new SettingsScreen();
        contentPanel.add(settingsScreen, "SETTINGS");

        cardLayout.show(contentPanel, "CHAT");

        // Add main panel to the frame
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public void reload() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        removeAll();
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
        EventHandlerService.getInstance().addObserver(mainScreen);
        contentPanel.add(mainScreen, "CHAT");

        // Friend screen
        FriendHomePage friendHomePage = new FriendHomePage(cardLayout, contentPanel);
        contentPanel.add(friendHomePage, "FRIENDS");

        // Settings screen
        SettingsScreen settingsScreen = new SettingsScreen();
        contentPanel.add(settingsScreen, "SETTINGS");

        cardLayout.show(contentPanel, "CHAT");

        // Add main panel to the frame
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public int getObserverId() {
        return ComponentIdContext.CHAT_LAYOUT_RELOAD;
    }

    @Override
    public void update(Object obj) {
        reload();
    }
}

