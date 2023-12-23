package com.hcmus.ui.friendscreen;

import com.hcmus.UserProfile;
import com.hcmus.models.Friend;
import com.hcmus.services.FriendService;
import com.hcmus.models.User;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.SearchBar;
import com.hcmus.ui.table.Table;
import com.hcmus.ui.table.UnixTimestampConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        contextMenu = new ContextMenu(table.getTable(), List.of("Add Friend"));
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

//        JMenuItem unfriend = contextMenu.getUnfriend();
//        unfriend.addActionListener(new UnfriendAction(table));

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
    public void getListNotFriend() {
        FriendService service = FriendService.getInstance();
        List<User> listNotFriend = new ArrayList<>();
        try {
            listNotFriend = service.findAllStrangers(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User stranger : listNotFriend){
//            System.out.println(stranger.getName());
//            System.out.println(stranger.getBirthday());
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
