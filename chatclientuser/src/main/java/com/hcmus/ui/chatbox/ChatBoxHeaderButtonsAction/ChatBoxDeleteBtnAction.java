package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.hcmus.ui.chatbox.ChatBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBoxDeleteBtnAction implements ActionListener {
    private ChatBox parent;
    public ChatBoxDeleteBtnAction(ChatBox chatBox) {
        this.parent = chatBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
