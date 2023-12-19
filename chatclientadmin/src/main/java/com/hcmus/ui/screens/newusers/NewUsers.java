package com.hcmus.ui.screens.newusers;

import com.hcmus.entities.user.User;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.DateRangeSelector;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;

public class NewUsers extends JPanel {
    private Table<User> table;
    private SearchBar searchBar;
    private UserService service;
    private DateRangeSelector dateRangeSelector;
    public NewUsers() {
        setLayout(new BorderLayout());
        try {
            service = new UserService();
            java.util.List<User> data = service.getAllUsers();
            java.util.List<String> columnNames = User.getColumnNames();

            table = new Table<>(data, columnNames);
            searchBar = new SearchBar(table.getSorter());
            dateRangeSelector = new DateRangeSelector();


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(dateRangeSelector, BorderLayout.SOUTH);
    }
}
