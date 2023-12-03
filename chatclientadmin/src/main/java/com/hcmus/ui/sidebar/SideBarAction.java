package com.hcmus.ui.sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideBarAction implements ActionListener {
    private String text;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public SideBarAction() {
    }

    public SideBarAction(String text, CardLayout cardLayout, JPanel contentPanel) {
        this.text = text;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(text);
        switch (text) {
            case "User list":
                cardLayout.show(contentPanel, "USER_LIST");
                break;
            case "Chat list":
                cardLayout.show(contentPanel, "CHAT_LIST");
                break;
            case "Login history":
                cardLayout.show(contentPanel, "LOGIN_HISTORY");
                break;
            case "New users":
                cardLayout.show(contentPanel, "NEW_USER");
                break;
            case "Active user statistics":
                cardLayout.show(contentPanel, "ONL_CHART");
                break;
            case "Online users":
                cardLayout.show(contentPanel, "ONLINE_USERS");
                break;
            case "Registration statistics":
                cardLayout.show(contentPanel, "REG_CHART");
                break;
            case "Spam list":
                cardLayout.show(contentPanel, "SPAM_LIST");
                break;
            case "Users' friends":
                cardLayout.show(contentPanel, "USER_FRIENDS");
                break;
        }
    }
}
