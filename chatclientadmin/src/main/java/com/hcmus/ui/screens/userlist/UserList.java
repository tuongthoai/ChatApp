package com.hcmus.ui.screens.userlist;

import com.hcmus.services.UserService;
import com.hcmus.ui.screens.userlist.add.AddAction;
import com.hcmus.ui.screens.userlist.block.BlockAction;
import com.hcmus.ui.screens.userlist.delete.DeleteAction;
import com.hcmus.ui.screens.userlist.edit.EditAction;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.Table;
import com.hcmus.entities.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserList extends JPanel {
    private Table<User> table;
    private ContextMenu contextMenu;
    private UserService service;

    public UserList() {
        try {
            service = new UserService();
            List<User> data = service.getAllUsers();
            List<String> columnNames = User.getColumnNames();

            table = new Table<User>(data, columnNames);
            contextMenu = new ContextMenu(table.getTable(), List.of("Add", "Edit", "Delete", "Block", "Detail"));

            // Add action listener for each menu item
            JMenuItem addItem = contextMenu.getAddItem();
            addItem.addActionListener(new AddAction(table));

            JMenuItem deleteItem = contextMenu.getDeleteItem();
            deleteItem.addActionListener(new DeleteAction(table));

            JMenuItem editItem = contextMenu.getEditItem();
            editItem.addActionListener(new EditAction(table));

            JMenuItem blockItem = contextMenu.getBlockItem();
            blockItem.addActionListener(new BlockAction(table));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
    }
}
