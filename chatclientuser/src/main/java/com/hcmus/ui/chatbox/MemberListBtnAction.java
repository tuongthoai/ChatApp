package com.hcmus.ui.chatbox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MemberListBtnAction implements ActionListener {
    private JPanel parent;

    public MemberListBtnAction() {}

    public MemberListBtnAction(JPanel parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> members = List.of("user1", "user22222222222", "user3");
        List<String> roles = List.of("admin", "member", "member");
        MemberListDialog memListDialog = new MemberListDialog(parent, members, roles);
        memListDialog.setVisible(true);
    }
}
