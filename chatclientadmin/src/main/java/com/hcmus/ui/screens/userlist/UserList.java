package com.hcmus.ui.screens.userlist;

import com.hcmus.services.UserService;
import com.hcmus.ui.screens.userlist.add.AddAction;
import com.hcmus.ui.screens.userlist.block.BlockAction;
import com.hcmus.ui.screens.userlist.delete.DeleteAction;
import com.hcmus.ui.screens.userlist.edit.EditAction;
import com.hcmus.ui.screens.userlist.friendlist.FriendListAction;
import com.hcmus.ui.screens.userlist.loginhistory.LoginHistoryAction;
import com.hcmus.ui.table.*;
import com.hcmus.entities.user.User;
import kotlin.collections.ArrayDeque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserList extends JPanel {
    private Table<User> table;
    private ContextMenu contextMenu;
    private FilterMenu filterMenu;
    private SearchBar searchBar;
    private UserService service;

    public UserList() {
        setLayout(new BorderLayout());
        try {
            service = new UserService();
            List<User> data = service.getAllUsers();
            List<String> columnNames = Arrays.asList("ID", "Username", "Password", "Name", "Email", "Sex", "Address", "Birthday", "Blocked", "Online", "Created Time");

            table = new Table<>(data, columnNames);
            contextMenu = new ContextMenu(table.getTable(), List.of("Add", "Edit", "Delete", "Block", "Detail", "Friend List", "Login History"));
            searchBar = new SearchBar(table.getSorter());

            JTextField username = new JTextField(10);
            JTextField password = new JTextField(10);
            JTextField name = new JTextField(10);
            JTextField email = new JTextField(10);
            JTextField sex = new JTextField(10);
            JComboBox<String> blocked = new JComboBox<>(new String[]{"All", "Blocked", "Unblocked"});
            JComboBox<String> online = new JComboBox<>(new String[]{"All", "Online", "Offline"});
            username.setName("Username");
            name.setName("name");
            FilterMenuBuilder filterMenuBuilder = new FilterMenuBuilder();
            filterMenuBuilder.setSorter(table.getSorter());
            filterMenuBuilder.setModel(table.getModel());
            filterMenuBuilder.setFilterName("Filter");
            filterMenuBuilder.setFilterComponents(new JComponent[]{username, name, blocked, online});
            filterMenuBuilder.setFilterLabels(new JLabel[]{new JLabel("Username"),  new JLabel("Name"), new JLabel("Blocked"), new JLabel("Online")});
            filterMenu = filterMenuBuilder.createFilterMenu();
//            StateFilterMenu stateFilterMenu = filterMenuBuilder.createStateFilterMenu();

            // Add action listener for each menu item
            JMenuItem addItem = contextMenu.getAddItem();
            addItem.addActionListener(new AddAction(table));

            JMenuItem deleteItem = contextMenu.getDeleteItem();
            deleteItem.addActionListener(new DeleteAction(table));

            JMenuItem editItem = contextMenu.getEditItem();
            editItem.addActionListener(new EditAction(table));

            JMenuItem blockItem = contextMenu.getBlockItem();
            blockItem.addActionListener(new BlockAction(table));

            JMenuItem friendListItem = contextMenu.getFriendListItem();
            friendListItem.addActionListener(new FriendListAction(table));

            JMenuItem loginHistoryItem = contextMenu.getLoginHisItem();
            loginHistoryItem.addActionListener(new LoginHistoryAction(table));

            JMenuItem refreshItem = contextMenu.getRefreshItem();
            refreshItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReloadTable<User>().reloadTable(table, service, User.class);
                }
            });

            contextMenu.setFilterMenu(filterMenu);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
