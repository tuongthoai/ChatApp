package com.hcmus.ui.screens.chatlist.adminlist;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.user.User;
import com.hcmus.services.GChatService;
import com.hcmus.ui.table.DetailList;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminListAction implements ActionListener {
    private Table<GroupChat> tablePanel;
    private GChatService gChatService;

    public AdminListAction() {}
    public AdminListAction(Table<GroupChat> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GroupChat groupChat = tablePanel.getSelectedData();
        gChatService = new GChatService();

        // Empty list
        List<User> admins = new ArrayList<>();

        try {
            admins = gChatService.getAllAdmins(groupChat.getGroupId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DetailList<User> adminsListDialog = null;
        try {
            adminsListDialog = new DetailList<User>(tablePanel, new Table<User>(admins, User.getColumnNames()), "Admins List - " + groupChat.getGroupName());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        adminsListDialog.setVisible(true);
    }
}
