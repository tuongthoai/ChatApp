package com.hcmus.ui.chatbox;

import javax.swing.*;
import java.util.List;

public class GroupInfoDialog extends JDialog {
    private JPanel parent;

    public GroupInfoDialog() {}

    public GroupInfoDialog(JPanel parent, List<String> members, List<String> roles) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), "Group chat information", true);
        this.parent = parent;

        MemberList memberList = new MemberList(members, roles);

        setSize(300, 400);
        setLocationRelativeTo(parent);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(new RenameField("Group name"));

        add(new JSeparator());
        // add a label
        JLabel memberLabel = new JLabel("Members");
        memberLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        memberLabel.setFont(memberLabel.getFont().deriveFont(16f));
        memberLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(memberLabel);

        add(memberList);
    }
}
