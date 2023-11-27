package com.hcmus.ui.sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel {
    private static final String currentDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    private static JPanel contentPanel;
    private static CardLayout cardLayout;
    private static JButton selectedButton, button1, button2, button3;

    public Sidebar() {}
    public Sidebar(JPanel contentPanel, CardLayout cardLayout) {
        Sidebar.cardLayout = cardLayout;
        Sidebar.contentPanel = contentPanel;

        setBackground(new Color(204, 255, 204));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        selectedButton = null;

        button1 = createButton("Chat", "chat-icon.png");
        button2 = createButton("Friends", "friends-icon.png");
        button3 = createButton("Settings", "settings-icon.png");

        add(button1);
        add(button2);
        add(button3);

        // Set the first button as selected
        button1.setBackground(new Color(153, 255, 153));
        selectedButton = button1;
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
        button.setBackground(new Color(204, 255, 204));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Add vertical spacing around the button
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15));
        button.addActionListener(new SideBarAction(text, cardLayout, contentPanel));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(153, 255, 153));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != selectedButton) {
                    button.setBackground(new Color(204, 255, 204));
                }
            }
        });

        // Add click effect
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedButton != null) {
                    selectedButton.setBackground(new Color(204, 255, 204));
                }
                button.setBackground(new Color(153, 255, 153));
                selectedButton = button;
            }
        });

        // Change the cursor to hand cursor
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    public static void setSelectedButton(JButton button) {
        selectedButton = button;
    }
}
