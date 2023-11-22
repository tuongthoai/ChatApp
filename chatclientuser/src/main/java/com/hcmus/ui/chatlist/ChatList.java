package com.hcmus.ui.chatlist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
public class ChatList extends JScrollPane {
    private static final String currentDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    public ChatList() {
        List<String> friends = Arrays.asList("User 1", "User 2", "User 3", "User 4", "User 5");
        List<String> chatList = Arrays.asList("All of those captains grab chemical, photonic parasites.", "Hi!!", "Goodbye", "Goodbye", "Goodbye");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 10; i++) {
            panel.add(new ChatListItem("avatar.png", friends.get(0), chatList.get(0)));
            if (i < 9) {
                panel.add(new JSeparator());
            }
        }
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setViewportView(panel);
    }
    private static class ChatListItem extends JButton {
        public ChatListItem(String avatarPath, String name, String lastMessage) {
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
            JLabel nameLabel = new JLabel(name.toUpperCase());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setBackground(Color.RED);
            subPanel.add(nameLabel);
            if (lastMessage.length() > 35) {
                lastMessage = lastMessage.substring(0, 35) + "...";
            }
            JTextArea lastMsg = new JTextArea(lastMessage);
            lastMsg.setLineWrap(true);
            lastMsg.setWrapStyleWord(true);
            lastMsg.setEditable(false);
            lastMsg.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            subPanel.add(lastMsg);
            subPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            JLabel avatarIcon = new JLabel(new ImageIcon(currentDir + avatarPath));
            avatarIcon.setBorder(new EmptyBorder(0, 0, 0, 10));
            add(avatarIcon, BorderLayout.WEST);
            add(subPanel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        }
    }
}
