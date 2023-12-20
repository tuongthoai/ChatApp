package com.hcmus.ui.screens.newusers;

import com.hcmus.entities.user.User;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.DateRangeSelector;
import com.hcmus.ui.table.FilterMenu;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NewUsers extends JPanel {
    private Table<User> table;
    private SearchBar searchBar;
    private UserService service;

    private FilterMenu filterMenu;
    private JPopupMenu contextMenu;
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

            JTextField name = new JTextField(10);
            name.setName("Name");

            filterMenu = new FilterMenu(table.getSorter(), table.getModel(), "Filter by Name");
            filterMenu.setFilterComponents(new JComponent[]{name});
            filterMenu.setFilterLabels(new JLabel[]{new JLabel("Name")});
            contextMenu = new JPopupMenu();
            contextMenu.add(filterMenu);


            // Add sort keys
            ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
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
                    java.util.List<User> filteredData = service.getNewUser(dateRangeSelector.getStartDate(), dateRangeSelector.getEndDate());
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
