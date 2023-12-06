package com.hcmus.ui.screens.userlist;

import com.hcmus.services.UserService;
import com.hcmus.ui.screens.userlist.add.AddAction;
import com.hcmus.ui.screens.userlist.block.BlockAction;
import com.hcmus.ui.screens.userlist.delete.DeleteAction;
import com.hcmus.ui.screens.userlist.edit.EditAction;
import com.hcmus.ui.screens.userlist.friendlist.FriendListAction;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.FilterMenu;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;
import com.hcmus.entities.user.User;

import javax.swing.*;
import java.awt.*;
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
            List<String> columnNames = User.getColumnNames();

            table = new Table<>(data, columnNames);
            contextMenu = new ContextMenu(table.getTable(), List.of("Add", "Edit", "Delete", "Block", "Detail", "Friend List"));
            filterMenu = new FilterMenu(table.getSorter(), table.getModel());
            searchBar = new SearchBar(table.getSorter());

            JTextField username = new JTextField(10);
            JTextField password = new JTextField(10);
            JTextField name = new JTextField(10);
            JTextField email = new JTextField(10);
            JTextField sex = new JTextField(10);
            username.setName("Username");
            password.setName("Password");
            name.setName("name");
            email.setName("email");
            sex.setName("sex");
            filterMenu.setFilterComponents(new JComponent[]{username, password, name, email, sex});
            filterMenu.setFilterLabels(new JLabel[]{new JLabel("Username"), new JLabel("Password"), new JLabel("Name"), new JLabel("Email"), new JLabel("Sex")});


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

            contextMenu.setFilterMenu(filterMenu);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
