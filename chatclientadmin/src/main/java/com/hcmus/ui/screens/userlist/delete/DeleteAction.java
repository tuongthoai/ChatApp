package com.hcmus.ui.screens.userlist.delete;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAction implements ActionListener {
    private Table<User> tablePanel;

    public DeleteAction() {}
    public DeleteAction(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DeleteDialog deleteDialog = new DeleteDialog(tablePanel);
        deleteDialog.setVisible(true);
    }
}
