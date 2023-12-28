package com.hcmus.ui.friendscreen.listfriend;

import com.hcmus.models.UserDTO;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockAction<T> implements ActionListener {
    private Table<T> tablePanel;
    private Class<?> clazz;

    public BlockAction() {}
    public BlockAction(Table<T> tablePanel, Class<?> clazz) {
        this.tablePanel = tablePanel;
        this.clazz = clazz;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BlockDialog blockDialog = new BlockDialog<T>(tablePanel, clazz);
        blockDialog.setVisible(true);
    }
}
