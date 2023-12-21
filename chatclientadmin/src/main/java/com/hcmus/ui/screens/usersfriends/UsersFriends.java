package com.hcmus.ui.screens.usersfriends;

import com.hcmus.entities.user.UserDTO;
import com.hcmus.services.UserFriendService;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.FilterMenu;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsersFriends extends JPanel {
    private Table<UserDTO> table;
    private SearchBar searchBar;
    private UserFriendService service;
    public UsersFriends() {
        try{
            service = new UserFriendService();
            List<UserDTO> userList = service.getDirInDirFriend();
            List<String> columnHead = service.getColumns();

//            for(UserDTO u : userList){
//                System.out.println(u.getName());
//                System.out.println(u.getDirectFriend());
//                System.out.println(u.getIndirectFriend());
//                System.out.println("==============");
//            }

            table = new Table<>(userList, columnHead);
            searchBar = new SearchBar(table.getSorter());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
