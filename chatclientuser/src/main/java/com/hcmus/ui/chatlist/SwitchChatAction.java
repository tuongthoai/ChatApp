package com.hcmus.ui.chatlist;

import com.hcmus.ChatHashMap;
import com.hcmus.ui.chatlayout.ChatScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchChatAction implements ActionListener {
    private final ChatScreen chatScreen;

    public SwitchChatAction(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chatScreen.updateChatBox(ChatHashMap.getInstance().getChat((ChatListItem) e.getSource()));
    }
}
