package com.hcmus.ui.chatlist;

import com.hcmus.ChatHashMap;
import com.hcmus.UserProfile;
import com.hcmus.models.GroupChat;
import com.hcmus.services.GChatService;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatlayout.ChatScreen;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatList extends JPanel {
    private JScrollPane mainPanel;
    private JButton addButton;
    private ChatScreen chatScreen;
    private List<String> friendNames;
    private List<GroupChat> groupChats;

    public ChatList(ChatScreen chatScreen) throws URISyntaxException {
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
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // init websocket
        Map<String, String> wsHeaders = new HashMap<>();
        URI wsUri = new URI("ws://localhost:8080/chat");
        wsHeaders.put("USER_SEND_ID", String.valueOf(UserProfile.getUserProfile().getId()));
        ChatContext context = ChatContext.getInstance(wsUri, wsHeaders);

        // init ChatHashMap
        ChatHashMap chatHashMap = ChatHashMap.getInstance();
        chatHashMap.setContext(context);

        int gchatSize = groupChats.size();
        for (int i = 0; i < gchatSize; i++) {
            gbc.gridy = i * 2;
            GroupChat groupChat = groupChats.get(i);

            gbc.anchor = GridBagConstraints.NORTH;

            ChatListItem chatListItem = new ChatListItem("avatar.png", groupChat.getGroupName(), chatList.get(0));
            chatHashMap.addChat(chatListItem, groupChat.getGroupId(), groupChat.getGroupName());
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
        addButton.addActionListener(new CreateConvoAction());
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
        GChatService gchatService = GChatService.getInstance();
        try {
            this.groupChats = gchatService.getGChatList(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
