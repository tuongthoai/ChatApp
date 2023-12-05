package com.hcmus.ui.chatlist;

import com.hcmus.ChatHashMap;
import com.hcmus.models.GroupChat;
import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatbox.ChatMessage;
import com.hcmus.ui.chatlayout.ChatScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwitchChatAction implements ActionListener {
    private ChatScreen chatScreen;

    public SwitchChatAction() {
    }

    public SwitchChatAction(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
//        chatMessages.add(new ChatMessage(username1, "LogoHCMUS.jpg", "Xin chao"));
//        chatMessages.add(new ChatMessage(username2, "LogoHCMUS.jpg", "Toi la Gia Thinh"));
//        chatMessages.add(new ChatMessage(username1, "", "Xin chao"));
//        chatMessages.add(new ChatMessage(username2, "LogoHCMUS.jpg", "Rat vui duoc tro chuyen voi ban"));
//        chatMessages.add(new ChatMessage(username1, "", "Toi cung vay"));

        chatScreen.updateChatBox(ChatHashMap.getChat((ChatListItem) e.getSource()));
    }
}
