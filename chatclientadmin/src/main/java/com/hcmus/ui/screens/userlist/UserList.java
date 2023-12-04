package com.hcmus.ui.screens.userlist;

import com.hcmus.ui.table.Table;
import com.hcmus.entities.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserList extends JPanel {
    private Table<User> table;
    private UserListService service;

    public UserList() {
        try {
            service = new UserListService();
            List<User> data = service.getAllUsers();
            List<String> columnNames = User.getColumnNames();
            table = new Table<User>(data, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
    }
}
