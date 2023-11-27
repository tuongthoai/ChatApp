package com.hcmus.ui.chatlist;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatbox.ChatMessage;
import com.hcmus.ui.chatlayout.ChatScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwitchChatAction implements ActionListener {
    private String username1;
    private String username2;
    private ChatScreen chatScreen;

    public SwitchChatAction() {}

    public SwitchChatAction(String username1, String username2, ChatScreen chatScreen) {
        this.username1 = username1;
        this.username2 = username2;
        this.chatScreen = chatScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(username1, "LogoHCMUS.jpg","Xin chao"));
        chatMessages.add(new ChatMessage(username2, "LogoHCMUS.jpg","Toi la Gia Thinh"));
        chatMessages.add(new ChatMessage(username1, "","Xin chao"));
        chatMessages.add(new ChatMessage(username2, "LogoHCMUS.jpg","Rat vui duoc tro chuyen voi ban"));
        chatMessages.add(new ChatMessage(username1, "","Toi cung vay"));
        chatScreen.updateChatBox(chatMessages);
    }
}
