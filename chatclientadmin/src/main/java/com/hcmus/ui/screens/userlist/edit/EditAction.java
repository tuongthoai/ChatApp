package com.hcmus.ui.screens.userlist.edit;

import com.hcmus.entities.user.User;
import com.hcmus.ui.screens.userlist.add.AddDialog;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAction implements ActionListener {
    private Table<User> tablePanel;

    public EditAction() {}
    public EditAction(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditDialog editDialog = new EditDialog(tablePanel);
        editDialog.setVisible(true);
    }
}
