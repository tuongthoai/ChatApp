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
        switch (text) {
            case "Chat":
                cardLayout.show(contentPanel, "CHAT");
                break;
            case "Friends":
                cardLayout.show(contentPanel, "FRIENDS");
                break;
            case "Settings":
                cardLayout.show(contentPanel, "SETTINGS");
                break;
            case "Login":
                cardLayout.show(contentPanel, "LOGIN");
                break;
        }
    }
}
