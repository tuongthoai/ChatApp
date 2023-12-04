package com.hcmus.ui.screens.userlist.add;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAction implements ActionListener {
    private Table<User> tablePanel;

    public AddAction() {}
    public AddAction(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddDialog addDialog = new AddDialog(tablePanel);
        addDialog.setVisible(true);
    }
}
