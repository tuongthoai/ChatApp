package com.hcmus.ui.friendscreen.blocklist;

import javax.swing.*;
import com.hcmus.models.*;
import com.hcmus.ui.chatlayout.ChatScreen;
import com.hcmus.ui.friendscreen.listfriend.BlockAction;
import com.hcmus.utils.UserProfile;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.ReloadTable;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockList extends JPanel {
    private CardLayout mainCard;
    private JPanel mainContentPanel;
    private Table<UserDTO> table;
    private SearchBar searchBar;
    private ContextMenu contextMenu;
    private List<UserDTO> blockList = new ArrayList<>();
    private ChatScreen chatScreen;
    public BlockList(CardLayout mainCard, JPanel mainContentPanel) throws SQLException {
        this.mainCard = mainCard;
        this.mainContentPanel = mainContentPanel;

        getBlockList();
        List<String> columnHeads = Arrays.asList("ID", "Username", "Fullname");

        table = new Table<>(this.blockList, columnHeads);
        searchBar = new SearchBar(table.getSorter());

        contextMenu = new ContextMenu(table.getTable(), List.of("Block/Unblock", "Refresh"));

        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("BLOCKED USERS");
        title.setFont(new Font("Serif", Font.BOLD, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth = 2;
        header.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        header.add(searchBar, gbc);

        JMenuItem block = contextMenu.getBlock();
        block.addActionListener(new BlockAction<UserDTO>(table, UserDTO.class));

        JMenuItem refresh = contextMenu.getRefresh();
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReloadTable.reloadBlockTable(table);
            }
        });

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
    public void getBlockList() {
        UserService service = UserService.getInstance();
        this.blockList.clear();
        List<User> blockList = new ArrayList<>();
        try {
            blockList = service.getAllBlockedUsers(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User friend : blockList){
            UserDTO userDTO = new UserDTO();

            userDTO.setId(friend.getId());
            userDTO.setUsername(friend.getUsername());
            userDTO.setFullname(friend.getName());
            if(friend.isOnline()){
                userDTO.setOnline("Online");
            } else {
                userDTO.setOnline("Offline");
            }

            this.blockList.add(userDTO);
        }
    }
}

