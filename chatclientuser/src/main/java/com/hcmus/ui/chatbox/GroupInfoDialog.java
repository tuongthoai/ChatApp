package com.hcmus.ui.chatbox;

import com.hcmus.models.GroupChatMember;

import javax.swing.*;
import java.util.List;

public class GroupInfoDialog extends JDialog {
    private ChatBox parent;

    public GroupInfoDialog() {
    }

    public GroupInfoDialog(ChatBox parent, List<GroupChatMember> members) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), parent.getChatName() + " Information", true);
        this.parent = parent;

        MemberList memberList = new MemberList(members);

        setSize(300, 400);
        setLocationRelativeTo(parent);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        RenameField renameField = new RenameField(parent);
        add(renameField);
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
