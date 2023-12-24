package com.hcmus.ui.friendscreen.listfriend;

import com.hcmus.utils.UserProfile;
import com.hcmus.models.User;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.ReloadTable;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;
import com.hcmus.models.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFriend extends JPanel {
    private CardLayout mainCard;
    private JPanel mainContentPanel;
    private Table<UserDTO> table;
    private SearchBar searchBar;
    private ContextMenu contextMenu;
    private List<UserDTO> listfriend = new ArrayList<>();
    public ListFriend(CardLayout mainCard, JPanel mainContentPanel) throws SQLException {
        this.mainCard = mainCard;
        this.mainContentPanel = mainContentPanel;

        getListFriend();
        List<String> columnHeads = Arrays.asList("ID", "Username", "Fullname", "Online");

        table = new Table<>(this.listfriend, columnHeads);
        searchBar = new SearchBar(table.getSorter());
        contextMenu = new ContextMenu(table.getTable(), List.of("Chat", "Unfriend", "Refresh"));

        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("LIST FRIENDS");
        title.setFont(new Font("Serif", Font.BOLD, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        header.add(title, gbc);

        gbc.gridy = 1;
        header.add(searchBar, gbc);

        JMenuItem unfriend = contextMenu.getUnfriend();
        unfriend.addActionListener(new UnfriendAction(table));

        JMenuItem refresh = contextMenu.getRefresh();
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReloadTable.reloadFriendTable(table);
            }
        });

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
    public void getListFriend() {
        UserService service = UserService.getInstance();
        List<User> listfriend = new ArrayList<>();
        try {
            listfriend = service.findAllFriends(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User friend : listfriend){
            UserDTO userDTO = new UserDTO();

            userDTO.setId(friend.getId());
            userDTO.setUsername(friend.getUsername());
            userDTO.setFullname(friend.getName());
            if(friend.isOnline()){
                userDTO.setOnline("Online");
            } else {
                userDTO.setOnline("Offline");
            }

            this.listfriend.add(userDTO);
        }
    }

}
