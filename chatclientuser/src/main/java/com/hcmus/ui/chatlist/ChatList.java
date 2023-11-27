package com.hcmus.ui.chatlist;

import com.hcmus.ui.chatlayout.ChatScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
public class ChatList extends JPanel {
    private final String currentDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    private JScrollPane mainPanel;
    private JButton addButton;
    private ChatScreen chatScreen;
    public ChatList(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        List<String> friends = Arrays.asList("User 1", "User 2", "User 3", "User 4", "User 5", "User 6", "User 7", "User 8", "User 9", "User 10");
        List<String> chatList = Arrays.asList("All of those captains grab chemical, photonic parasites.", "Hi!!", "Goodbye", "Goodbye", "Goodbye");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < 10; i++) {
            gbc.gridy = i * 2; // Adjusting gridy to skip a row for JSeparator
            panel.add(new ChatListItem("avatar.png", friends.get(i), chatList.get(0)), gbc);

            if (i < 9) {
                gbc.gridy = i * 2 + 1;
                gbc.weightx = 1.0; // Make the separator expand horizontally
                panel.add(new JSeparator(), gbc);
                gbc.weightx = 0.0; // Reset weightx
            }
        }

        addButton = new JButton("Create a new conversation");
        addButton.addActionListener(new AddConvoAction());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 10;
        add(addButton, gbc);

        mainPanel = new JScrollPane(panel);
        mainPanel.setPreferredSize(new Dimension(200, 400));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(mainPanel, gbc);
    }
    private class ChatListItem extends JButton {
        public ChatListItem(String avatarPath, String name, String lastMessage) {
            setBackground(Color.WHITE);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            JPanel subPanel = new JPanel();
            subPanel.setLayout(new GridBagLayout());
            subPanel.setBackground(Color.WHITE);

            // Name label
            JLabel nameLabel = new JLabel(name.toUpperCase());
            nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            subPanel.add(nameLabel, gbc);

            // Last message text area
            if (lastMessage.length() > 35) {
                lastMessage = lastMessage.substring(0, 35) + "...";
            }
            JTextArea lastMsg = new JTextArea(lastMessage);
            lastMsg.setLineWrap(true);
            lastMsg.setWrapStyleWord(true);
            lastMsg.setEditable(false);
            lastMsg.setFont(new Font("Tahoma", Font.PLAIN, 10));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 0, 0, 0);
            subPanel.add(lastMsg, gbc);

            subPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            // Avatar icon
            JLabel avatarIcon = new JLabel(new ImageIcon(currentDir + avatarPath));
            avatarIcon.setBorder(new EmptyBorder(0, 0, 0, 10));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(avatarIcon, gbc);

            // Subpanel containing name and last message
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            add(subPanel, gbc);

            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            addActionListener(new SwitchChatAction(name, name + "a", chatScreen));
        }
    }
}
