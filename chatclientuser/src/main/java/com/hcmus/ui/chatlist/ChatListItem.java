package com.hcmus.ui.chatlist;

import com.hcmus.Link;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChatListItem extends JButton {

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
        JLabel avatarIcon = new JLabel(new ImageIcon(Link.getLink("image") + avatarPath));
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
    }
}
