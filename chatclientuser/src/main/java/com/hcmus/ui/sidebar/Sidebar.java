package com.hcmus.ui.sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel {
    private static final String currentDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    public Sidebar() {
        setBackground(new Color(51, 153, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton button1 = createButton("Chat", "chat-icon.png");
        JButton button2 = createButton("Friends", "friends-icon.png");
        JButton button3 = createButton("Settings", "settings-icon.png");

        add(button1);
        add(button2);
        add(button3);
    }

    private static JButton createButton(String text, String iconFilename) {
        JButton button = new JButton();

        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
//        button.setText(text);

        // Load the icon
        ImageIcon icon = new ImageIcon(currentDir + iconFilename);
        button.setIcon(icon);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Add vertical spacing around the button
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15));
        button.addActionListener(new SideBarAction(text));
        return button;
    }
}
