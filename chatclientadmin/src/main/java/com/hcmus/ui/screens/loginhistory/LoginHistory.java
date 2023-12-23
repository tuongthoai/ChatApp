package com.hcmus.ui.screens.loginhistory;

import com.hcmus.entities.loginhistory.UserLoginTime;
import com.hcmus.entities.user.User;
import com.hcmus.entities.user.UserActivity;
import com.hcmus.services.LoginHistoryService;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginHistory extends JPanel {
    private Table<UserLoginTime> table;
    private SearchBar searchBar;
    private LoginHistoryService service;

    public LoginHistory() {
        setLayout(new BorderLayout());
        try {
            service = new LoginHistoryService();
            java.util.List<UserLoginTime> data = service.getUserLoginTime();
            java.util.List<String> columnNames = UserLoginTime.getColumnNames();

            table = new Table<>(data, columnNames);
            searchBar = new SearchBar(table.getSorter());
            ContextMenu contextMenu = new ContextMenu(table.getTable(), new ArrayList<>());
            JMenuItem refreshItem = contextMenu.getRefreshItem();
            refreshItem.addActionListener(e -> new ReloadTable<UserLoginTime>().reloadTable(table, service, UserLoginTime.class));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
