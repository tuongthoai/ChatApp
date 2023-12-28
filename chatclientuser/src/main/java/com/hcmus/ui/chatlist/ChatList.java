package com.hcmus.ui.chatlist;

import com.hcmus.utils.ChatHashMap;
import com.hcmus.utils.UserProfile;
import com.hcmus.models.GroupChat;
import com.hcmus.services.GChatService;
import com.hcmus.ui.chatlayout.ChatScreen;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChatList extends JPanel {
    private JScrollPane mainPanel;
    private JButton addButton;
    private ChatScreen chatScreen;
    private List<GroupChat> groupChats;
    private JPanel panel = new JPanel();

    public ChatList(ChatScreen chatScreen) throws URISyntaxException {
        this.chatScreen = chatScreen;
        setLayout(new GridBagLayout());

        initListGroup("");
    }

    private void getGChatData(String keyword) {
        this.groupChats = null;
        GChatService gchatService = GChatService.getInstance();
        try {
            List<GroupChat> allGroupChats = gchatService.getGChatList(UserProfile.getUserProfile().getId());

            // Filter groupChats based on the keyword
            this.groupChats = allGroupChats.stream()
                    .filter(groupChat -> groupChat.getGroupName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListGroup(String keyword){
        getGChatData(keyword);
        for(GroupChat groupChat : this.groupChats){
            System.out.println(groupChat.getGroupName());
        }
        GridBagConstraints gbc = new GridBagConstraints();

        List<String> chatList = Arrays.asList("All of those captains grab chemical, photonic parasites.", "Hi!!", "Goodbye", "Goodbye", "Goodbye");

        this.panel = new JPanel();
        this.panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // init ChatHashMap
        ChatHashMap chatHashMap = ChatHashMap.getInstance();
        chatHashMap.setContext(this.chatScreen.getContext());

        // init service for get history msg
        GChatService service = GChatService.getInstance();

        int gchatSize = groupChats.size();
        System.out.println(gchatSize);
        for (int i = 0; i < gchatSize; i++) {
            gbc.gridy = i * 2;
            GroupChat groupChat = groupChats.get(i);

            gbc.anchor = GridBagConstraints.NORTH;

//            chatHashMap.removeAll();
            ChatListItem chatListItem = new ChatListItem("avatar.png", groupChat.getGroupName(), chatList.get(0));
            chatHashMap.addChat(chatListItem, groupChat.getGroupId(), groupChat.getGroupName(), service.getAllHistory(groupChat.getGroupId()));
            chatListItem.addActionListener(new SwitchChatAction(chatScreen));
            this.panel.add(chatListItem, gbc);

            if (i < gchatSize - 1) {
                gbc.gridy = i * 2 + 1;
                gbc.weightx = 1.0; // Make the separator expand horizontally
                this.panel.add(new JSeparator(), gbc);
                gbc.weightx = 0.0;
            }
        }

        addButton = new JButton("Create a new conversation");
        addButton.addActionListener(new CreateConvoAction(this));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 10;
        add(addButton, gbc);


        mainPanel = new JScrollPane(this.panel);
        mainPanel.setPreferredSize(new Dimension(200, 400));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(mainPanel, gbc);
    }
    public void updateChatList(String keyword){
//        System.out.println(keyword);
        removeAll();
        initListGroup(keyword);
        revalidate();
        repaint();
    }
}
