package com.hcmus.ui.screens;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Layout extends JFrame {
    public Layout() throws SQLException {
        setTitle("Chat management system");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Test data for table
        List<User> list = new ArrayList<>();

        User user1 = new User(1, "test_user 1", "test_password 1", "Test User 1", "test1@gmail.com", "male", "Ha noi", 946684800000L, 1641022800000L, true, false);
        User user2 = new User(2, "test_user2", "test_password2", "Test User 2", "test2@gmail.com", "female", "Ho Chi Minh City", 978307200000L, 1639275600000L, false, true);
        User user3 = new User(3, "test_user3", "test_password3", "Test User 3", "test3@gmail.com", "female", "Da Nang", 915148800000L, 1643701200000L, true, true);
        User user4 = new User(4, "test_user4", "test_password4", "Test User 4", "test4@gmail.com", "male", "Hue", 1007683200000L, 1641354000000L, false, false);
        User user5 = new User(5, "test_user5", "test_password5", "Test User 5", "test5@gmail.com", "female", "Can Tho", 987766800000L, 1639736400000L, true, true);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        List<String> columnNames = User.getColumnNames();
        Table<User> table = new Table<>(list, columnNames);
        List<String> options = Arrays.asList(
                "Edit",
                "Delete",
                "Block",
                "Detail",
                "Login History",
                "Friend List",
                "Member List",
                "Admin List"
        );
        ContextMenu contextMenu = new ContextMenu(table.getTable(), options);
        SearchBar searchBar = new SearchBar(table.getSorter());
        FilterMenu filterMenu = new FilterMenuBuilder().setSorter(table.getSorter()).setModel(table.getModel()).setFilterName("Filter").createFilterMenu();


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


        // add menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(filterMenu);
        setJMenuBar(menuBar);


        add(searchBar, "North");
        add(table);
        setVisible(true);
    }
}
