package com.hcmus.ui.screens.onlineusers;

import com.hcmus.entities.user.UserActivity;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class OnlineUsers extends JPanel {
    private Table<UserActivity> table;
    private SearchBar searchBar;
    private UserService service;
    private JPopupMenu contextMenu;
    private FilterMenu filterMenu;
    private DateRangeSelector dateRangeSelector;
    public OnlineUsers() {
        setLayout(new BorderLayout());
        try {
            service = new UserService();
            java.util.List<UserActivity> data = service.getAllUserActivity();
            java.util.List<String> columnNames = UserActivity.getColumnNames();

            table = new Table<>(data, columnNames);
            searchBar = new SearchBar(table.getSorter());
            dateRangeSelector = new DateRangeSelector();

            JTextField loginCount = new JTextField(10);
            loginCount.setName("Login Count");
            FilterMenuBuilder filterBuilder = new FilterMenuBuilder().setSorter(table.getSorter()).setModel(table.getModel()).setFilterName("Filter by Login Count");
            filterBuilder.setFilterComponents(new JComponent[]{loginCount});
            filterBuilder.setFilterLabels(new JLabel[]{new JLabel("Login Count")});
            NumberFilterMenu loginCountFilterMenu = filterBuilder.createNumberFilterMenu();

            filterBuilder.setFilterName("Filter by Name");
            JTextField name = new JTextField(10);
            name.setName("Name");
            filterBuilder.setFilterComponents(new JComponent[]{name});
            filterBuilder.setFilterLabels(new JLabel[]{new JLabel("Name")});
            filterMenu = filterBuilder.createFilterMenu();

            contextMenu = new JPopupMenu();
            contextMenu.add(loginCountFilterMenu);
            contextMenu.add(filterMenu);

            // Add sort keys
            ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new RowSorter.SortKey(3, SortOrder.UNSORTED));
            sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
            sortKeys.add(new RowSorter.SortKey(8, SortOrder.ASCENDING));
            table.getSorter().setSortKeys(sortKeys);
            table.getTable().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        contextMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        contextMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });

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
            JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(dateRangeSelector, BorderLayout.SOUTH);
    }
}
