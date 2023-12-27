package com.hcmus.ui.chatlayout;

import com.hcmus.utils.ChatHashMap;
import com.hcmus.observer.Subscriber;
import com.hcmus.services.ComponentIdContext;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatlist.ChatList;
import com.hcmus.ui.searchbar.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;

public class ChatScreen extends JPanel implements Subscriber {
    JPanel chatBoxContainer;
    ChatBox nullChatBox;
    ChatContext context;
    JPanel mainPanel;

    public ChatScreen(ChatContext context) throws URISyntaxException {
        this.context = context;
        setLayout(new BorderLayout());
        initChatScreen();
        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    public void initChatScreen() throws URISyntaxException {
        ChatHashMap.getInstance().setChatScreen(this);
        // Main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());

        // List of conversations (you can use JList or other components)
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BorderLayout());
        JPanel conversationScrollPane = new ChatList(this);
        conversationScrollPane.setPreferredSize(new Dimension(220, 400));
        chatListPanel.add(new SearchBar(), BorderLayout.NORTH);
        chatListPanel.add(conversationScrollPane, BorderLayout.CENTER);
        chatListPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Message box
        nullChatBox = new ChatBox();
        chatBoxContainer = new JPanel();
        chatBoxContainer.setLayout(new BorderLayout());
        chatBoxContainer.add(nullChatBox, BorderLayout.CENTER);

        // Add components to main panel
        mainPanel.add(chatListPanel, BorderLayout.WEST);
        mainPanel.add(chatBoxContainer, BorderLayout.CENTER);
    }

    public void updateChatBox(ChatBox newChatBox) {
        chatBoxContainer.removeAll();
        chatBoxContainer.add(newChatBox, BorderLayout.CENTER);
        chatBoxContainer.revalidate();
        chatBoxContainer.repaint();
    }

    public void reloadChatScreen() throws URISyntaxException {
        removeAll();
        ChatHashMap.getInstance().clear();
        initChatScreen();
        add(mainPanel, BorderLayout.CENTER);
        chatBoxContainer.revalidate();
        chatBoxContainer.repaint();
    }

    public ChatContext getContext() {
        return context;
    }

    public void setContext(ChatContext context) {
        this.context = context;
    }

    @Override
    public int getObserverId() {
        return ComponentIdContext.CHAT_SCREEN_ID; // indicate ChatScreen
    }

    @Override
    public void update(Object obj) {
        // Cast to string and parse the meaning
        String msg = (String) obj;
        try {
            if (msg == null) {
                reloadChatScreen();
            } else {
                reloadChatScreen();
                String[] tmps = msg.split("->");
                updateChatBox(ChatHashMap.getInstance().getChatBoxFromId(Integer.valueOf(tmps[1])));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
