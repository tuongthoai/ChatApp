package com.hcmus.ui.screens.chatlist.memberlist;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.user.User;
import com.hcmus.services.FriendService;
import com.hcmus.services.GChatService;
import com.hcmus.ui.table.DetailList;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberListAction implements ActionListener {
    private Table<GroupChat> tablePanel;
    private GChatService gChatService;

    public MemberListAction() {}
    public MemberListAction(Table<GroupChat> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GroupChat groupChat = tablePanel.getSelectedData();
        gChatService = new GChatService();

        // Empty list
        List<User> members = new ArrayList<>();

        try {
            members = gChatService.getAllMembers(groupChat.getGroupId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DetailList<User> memberListDialog = null;
        try {
            memberListDialog = new DetailList<User>(tablePanel, new Table<User>(members, User.getColumnNames()), "Members List - " + groupChat.getGroupName());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        memberListDialog.setVisible(true);
    }
}
