package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.hcmus.ui.chatbox.ChatBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupChatSpamBtnAction implements ActionListener {
    private ChatBox parent;

    public GroupChatSpamBtnAction(ChatBox parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Spam button clicked");
    }
}
