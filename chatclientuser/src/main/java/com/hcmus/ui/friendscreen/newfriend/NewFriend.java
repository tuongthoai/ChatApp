package com.hcmus.ui.friendscreen.newfriend;

import com.hcmus.services.UserService;
import com.hcmus.ui.friendscreen.listfriend.BlockAction;
import com.hcmus.utils.UserProfile;
import com.hcmus.models.Friend;
import com.hcmus.services.FriendService;
import com.hcmus.models.User;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class NewFriend extends JPanel {
    private CardLayout mainCard;
    private JPanel mainContentPanel;
    private Table<Friend> table;
    private SearchBar searchBar;
    private ContextMenu contextMenu;
    private List<Friend> listNotfriend = new ArrayList<>();
    public NewFriend(CardLayout mainCard, JPanel mainContentPanel) throws SQLException {
        this.mainCard = mainCard;
        this.mainContentPanel = mainContentPanel;

        getListNotFriend();
        List<String> columnHeads = Arrays.asList("ID", "Username", "Fullname", "Sex", "Birthday");
//
        table = new Table<>(this.listNotfriend, columnHeads);
        searchBar = new SearchBar(table.getSorter());
        contextMenu = new ContextMenu(table.getTable(), List.of("Add Friend", "Block/Unblock", "Refresh"));
//
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(Color.WHITE);
//
        JLabel title = new JLabel("FRIEND SUGGESTIONS");
        title.setFont(new Font("Serif", Font.BOLD, 28));
//
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        header.add(title, gbc);

        gbc.gridy = 1;
        header.add(searchBar, gbc);

        JMenuItem addfriend = contextMenu.getAddfriend();
        addfriend.addActionListener(new AddfriendAction(table));

        JMenuItem block = contextMenu.getBlock();
        block.addActionListener(new BlockAction<Friend>(table, Friend.class));

        JMenuItem refresh = contextMenu.getRefresh();
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReloadTable.reloadStrangerTable(table);
            }
        });

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
    public void getListNotFriend() {
        FriendService service = FriendService.getInstance();
        List<User> listNotFriend = new ArrayList<>();
        List<User> blockList = new ArrayList<>();
        try {
            listNotFriend = service.findAllStrangers(UserProfile.getUserProfile().getId());
            System.out.println(listNotFriend);
            System.out.println("________________________________");
            blockList = UserService.getInstance().getAllBlockedUsers(UserProfile.getUserProfile().getId());
            System.out.println(blockList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User stranger : listNotFriend){
            boolean isBlocked = false;

            for (User blocked : blockList) {
                if (stranger.getId() == blocked.getId()) {
                    isBlocked = true;
                    break;
                }
            }
            if (isBlocked) {
                continue;
            }

            Friend newFriend = new Friend();

            newFriend.setId(stranger.getId());
            newFriend.setUsername(stranger.getUsername());
            newFriend.setFullname(stranger.getName());
            newFriend.setSex(stranger.getSex());

            LocalDate date = UnixTimestampConverter.unix2DateTime((long) stranger.getBirthday());
            newFriend.setBirthday(date);

            this.listNotfriend.add(newFriend);
        }
    }

}
