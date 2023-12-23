package com.hcmus.ui.friendscreen.listfriend;

import com.hcmus.models.UserDTO;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnfriendAction implements ActionListener {
    private Table<UserDTO> tablePanel;

    public UnfriendAction() {}
    public UnfriendAction(Table<UserDTO> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UnfriendDialog unfriendDialog = new UnfriendDialog(tablePanel);
        unfriendDialog.setVisible(true);
    }
}
