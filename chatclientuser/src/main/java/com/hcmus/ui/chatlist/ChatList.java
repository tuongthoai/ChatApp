package com.hcmus.ui.chatlist;

import com.hcmus.ChatHashMap;
import com.hcmus.UserProfile;
import com.hcmus.models.GroupChat;
import com.hcmus.services.GChatService;
import com.hcmus.ui.chatlayout.ChatScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
public class ChatList extends JPanel {
    private JScrollPane mainPanel;
    private JButton addButton;
    private ChatScreen chatScreen;
    private List<String> friendNames;
    private List<GroupChat> groupChats;
    public ChatList(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Lấy all gchat của user này
        getGChatData();

        List<String> chatList = Arrays.asList("All of those captains grab chemical, photonic parasites.", "Hi!!", "Goodbye", "Goodbye", "Goodbye");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int gchatSize = groupChats.size();
        for (int i = 0; i < gchatSize; i++) {
            gbc.gridy = i * 2;
            ChatListItem chatListItem = new ChatListItem("avatar.png", groupChats.get(i).getGroupName(), chatList.get(0));
            ChatHashMap.addChat(chatListItem, groupChats.get(i).getGroupId(), groupChats.get(i).getGroupName());
            chatListItem.addActionListener(new SwitchChatAction(chatScreen));
            panel.add(chatListItem, gbc);

            if (i < gchatSize - 1) {
                gbc.gridy = i * 2 + 1;
                gbc.weightx = 1.0; // Make the separator expand horizontally
                panel.add(new JSeparator(), gbc);
                gbc.weightx = 0.0;
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

    private void getGChatData() {
        GChatService gchatService = new GChatService();
        try {
            this.groupChats = gchatService.getGChatList(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
