package com.hcmus.ui.chatbox;

import javax.swing.*;
import java.util.List;

public class MemberListDialog extends JDialog {
    private JPanel parent;

    public MemberListDialog() {}

    public MemberListDialog(JPanel parent, List<String> members, List<String> roles) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), "Members", true);
        this.parent = parent;

        MemberList memberList = new MemberList(members, roles);

        setSize(300, 400);
        setLocationRelativeTo(parent);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(memberList);
    }
}
