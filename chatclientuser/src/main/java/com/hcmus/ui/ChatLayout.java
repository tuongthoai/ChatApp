package com.hcmus.ui;

import javax.swing.*;
import java.awt.*;

public class ChatLayout extends JFrame {
    public ChatLayout() {
        setTitle("Chat UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar with GridBagLayout
        JPanel sidebarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        // Add sidebar components here
        String currentDir = System.getProperty("user.dir") + "/src/main/java/org/example/";
        JButton button1 = new JButton(new ImageIcon(currentDir + "chat-icon.png"));
        JButton button2 = new JButton(new ImageIcon(currentDir + "chat-icon.png"));
        JButton button3 = new JButton(new ImageIcon(currentDir + "chat-icon.png"));
        JButton button4 = new JButton(new ImageIcon(currentDir + "chat-icon.png"));
        sidebarPanel.add(button1, gbc);
        gbc.gridy++;
        sidebarPanel.add(button2, gbc);
        gbc.gridy++;
        sidebarPanel.add(button3, gbc);
        gbc.gridy++;
        sidebarPanel.add(button4, gbc);

        // List of conversations (you can use JList or other components)
        JList<String> conversationList = new JList<>(new String[]{"Conversation 1", "Conversation 2"});
        JScrollPane conversationScrollPane = new JScrollPane(conversationList);
        conversationScrollPane.setPreferredSize(new Dimension(200, 400));

        // Message box
        JTextArea messageBox = new JTextArea();
        JScrollPane messageScrollPane = new JScrollPane(messageBox);
        messageScrollPane.setPreferredSize(new Dimension(500, 400));

        // Add components to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(conversationScrollPane, BorderLayout.CENTER);
        mainPanel.add(messageScrollPane, BorderLayout.EAST);

        // Add main panel to the frame
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatLayout().setVisible(true);
        });
    }
}

