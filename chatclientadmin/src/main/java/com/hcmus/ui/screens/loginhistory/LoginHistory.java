package com.hcmus.ui.screens.loginhistory;

import com.hcmus.entities.user.User;
import com.hcmus.entities.user.UserActivity;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginHistory extends JPanel {
    private Table<UserActivity> table;
    private SearchBar searchBar;
    private UserService service;

    private DateRangeSelector dateRangeSelector;

    public LoginHistory() {
        setLayout(new BorderLayout());
        try {
            service = new UserService();
            java.util.List<UserActivity> data = service.getAllUserActivity();
            java.util.List<String> columnNames = UserActivity.getColumnNames();

            table = new Table<>(data, columnNames);
            searchBar = new SearchBar(table.getSorter());
            dateRangeSelector = new DateRangeSelector();

            // add event listener to date range selector
            dateRangeSelector.getSearchButton().addActionListener(e -> {
                try {
                    java.util.List<UserActivity> filteredData = service.getUserActivity(dateRangeSelector.getStartDate(), dateRangeSelector.getEndDate());
                    System.out.println(filteredData.size());
                    table.updateData(filteredData);
                    table.updateTable();
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            });

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(dateRangeSelector, BorderLayout.SOUTH);
    }
}
