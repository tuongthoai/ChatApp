package com.hcmus.ui.screens.loginhistory;

import com.hcmus.entities.user.User;
import com.hcmus.services.UserService;
import com.hcmus.ui.screens.userlist.add.AddAction;
import com.hcmus.ui.screens.userlist.block.BlockAction;
import com.hcmus.ui.screens.userlist.delete.DeleteAction;
import com.hcmus.ui.screens.userlist.edit.EditAction;
import com.hcmus.ui.screens.userlist.friendlist.FriendListAction;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginHistory extends JPanel {
    private Table<User> table;
    private SearchBar searchBar;
    private UserService service;

    public LoginHistory() {
        setLayout(new BorderLayout());
        try {
            service = new UserService();
            java.util.List<User> data = service.getAllUsers();
            java.util.List<String> columnNames = User.getColumnNames();

            table = new Table<>(data, columnNames);
            searchBar = new SearchBar(table.getSorter());


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
