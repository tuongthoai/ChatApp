package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatbox.GroupInfoDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GroupInfoBtnAction implements ActionListener {
    private ChatBox parent;

    public GroupInfoBtnAction() {}

    public GroupInfoBtnAction(ChatBox parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // CALL API FOR CHAT INFO

        List<String> members = List.of("user1", "user22222222222", "user3");
        List<String> roles = List.of("admin", "member", "member");
        GroupInfoDialog memListDialog = new GroupInfoDialog(parent, members, roles);
        memListDialog.setVisible(true);
    }
}
