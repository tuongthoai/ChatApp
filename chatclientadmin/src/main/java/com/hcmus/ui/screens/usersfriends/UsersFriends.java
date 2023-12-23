package com.hcmus.ui.screens.usersfriends;

import com.hcmus.entities.user.User;
import com.hcmus.entities.user.UserDTO;
import com.hcmus.services.UserFriendService;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UsersFriends extends JPanel {
    private Table<UserDTO> table;
    private SearchBar searchBar;
    private UserFriendService service;
    private JPopupMenu contextMenu;
    private FilterMenu filterMenu;
    public UsersFriends() {
        try{
            service = new UserFriendService();
            List<UserDTO> userList = service.getDirInDirFriend();
//            for(UserDTO user: userList){
//                System.out.println(user.getFriends());
//            }
            List<String> columnHead = service.getColumns();

            table = new Table<>(userList, columnHead);
            searchBar = new SearchBar(table.getSorter());

            JTextField friends = new JTextField(10);
            friends.setName("Friends");
            FilterMenuBuilder filterBuilder = new FilterMenuBuilder().setSorter(table.getSorter()).setModel(table.getModel()).setFilterName("Filter by direct friends");
            filterBuilder.setFilterComponents(new JComponent[]{friends});
            filterBuilder.setFilterLabels(new JLabel[]{new JLabel("Friends")});
            NumberFilterMenu loginCountFilterMenu = filterBuilder.createNumberFilterMenu();

            contextMenu = new JPopupMenu();
            contextMenu.add(loginCountFilterMenu);

            JMenuItem refreshItem = new JMenuItem("Refresh");
            refreshItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReloadTable<UserDTO>().reloadTable(table, service, UserDTO.class);
                }
            });
            contextMenu.add(refreshItem);

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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.err);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
