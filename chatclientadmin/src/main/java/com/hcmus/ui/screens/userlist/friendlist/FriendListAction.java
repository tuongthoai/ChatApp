package com.hcmus.ui.screens.userlist.friendlist;

import com.hcmus.entities.user.User;
import com.hcmus.services.FriendService;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FriendListAction implements ActionListener {
    private Table<User> tablePanel;
    private FriendService friendService;

    public FriendListAction() {}
    public FriendListAction(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = tablePanel.getSelectedData();
        friendService = new FriendService();

        List<User> friends = null;

        try {
            friends = friendService.getAllFriends(user.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 1));
        try {
            contentPanel.add(new Table<User>(friends, User.getColumnNames()));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        FriendListDialog friendListDialog = new FriendListDialog(tablePanel, contentPanel, user.getUsername());
        friendListDialog.setVisible(true);
    }
}
