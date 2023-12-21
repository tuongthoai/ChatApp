package com.hcmus.ui.chatbox.MemberListAction;

import com.hcmus.models.GroupChatMember;
import com.hcmus.ui.chatbox.MemberList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MemberListActionimpl implements ActionListener {
    private MemberList parent;

    public MemberListActionimpl(MemberList parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<GroupChatMember> members = parent.getMembers();

        GroupChatMember newMem = new GroupChatMember();
        newMem.setUserId(10000);
        newMem.setUsername("TEST_USER");
        newMem.setRole("USER_ROLE");

        members.add(newMem);

        parent.updateMemberList();
    }
}
