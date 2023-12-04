package com.hcmus.ui.screens.userlist.block;

import com.hcmus.entities.user.User;
import com.hcmus.ui.screens.userlist.block.BlockDialog;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockAction implements ActionListener {
    private Table<User> tablePanel;

    public BlockAction() {}
    public BlockAction(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BlockDialog blockDialog = new BlockDialog(tablePanel);
        blockDialog.setVisible(true);
    }
}