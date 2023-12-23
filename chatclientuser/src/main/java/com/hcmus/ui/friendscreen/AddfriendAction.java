package com.hcmus.ui.friendscreen;

import com.hcmus.models.Friend;
import com.hcmus.models.UserDTO;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddfriendAction implements ActionListener {
    private Table<Friend> tablePanel;

    public AddfriendAction() {}
    public AddfriendAction(Table<Friend> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddfriendDialog addfriendDialog = new AddfriendDialog(tablePanel);
        addfriendDialog.setVisible(true);
    }
}
