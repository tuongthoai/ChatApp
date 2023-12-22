package com.hcmus.ui.chatbox;

import com.hcmus.models.GroupChatMember;
import com.hcmus.services.EventHandlerService;
import com.hcmus.services.GChatService;

import javax.swing.*;
import java.util.List;

public class GroupInfoDialog extends JDialog {
    private ChatBox parent;

    public GroupInfoDialog() {
    }

    public GroupInfoDialog(ChatBox parent) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), "Group Information", true);
        this.parent = parent;
        GChatService service = GChatService.getInstance();
        List<GroupChatMember> members = service.getGroupChatMembers(parent.getChatId());
        EventHandlerService eventHandlerService = EventHandlerService.getInstance();
        MemberList memberList = new MemberList(members, parent.getChatId());
        eventHandlerService.addObserver(memberList);

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
