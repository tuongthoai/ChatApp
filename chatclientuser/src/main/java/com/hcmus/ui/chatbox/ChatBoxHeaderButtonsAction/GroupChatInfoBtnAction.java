package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.hcmus.models.GroupChatMember;
import com.hcmus.services.GChatService;
import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatbox.GroupInfoDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GroupChatInfoBtnAction implements ActionListener {
    private ChatBox parent;

    public GroupChatInfoBtnAction() {
    }

    public GroupChatInfoBtnAction(ChatBox parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GroupInfoDialog memListDialog = new GroupInfoDialog(parent);
        memListDialog.setVisible(true);
    }
}
