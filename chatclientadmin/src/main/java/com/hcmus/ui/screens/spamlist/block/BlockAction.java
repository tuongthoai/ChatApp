package com.hcmus.ui.screens.spamlist.block;

import com.hcmus.entities.spam.SpamReport;
import com.hcmus.entities.user.User;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockAction implements ActionListener {
    private Table<SpamReport> tablePanel;

    public BlockAction() {}
    public BlockAction(Table<SpamReport> tablePanel) {
        this.tablePanel = tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BlockDialog blockDialog = new BlockDialog(tablePanel);
        blockDialog.setVisible(true);
    }
}