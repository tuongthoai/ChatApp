package com.hcmus.ui.friendscreen.listfriend;

import com.hcmus.models.GroupChat;
import com.hcmus.models.GroupChatMember;
import com.hcmus.services.ComponentIdContext;
import com.hcmus.services.EventHandlerService;
import com.hcmus.services.GChatService;
import com.hcmus.ui.chatlayout.ChatScreen;
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
    private ChatScreen chatScreen;
    public ListFriend(CardLayout mainCard, JPanel mainContentPanel) throws SQLException {
        this.mainCard = mainCard;
        this.mainContentPanel = mainContentPanel;

        getListFriend();
        List<String> columnHeads = Arrays.asList("ID", "Username", "Fullname", "Online");

        table = new Table<>(this.listfriend, columnHeads);
        searchBar = new SearchBar(table.getSorter());

        contextMenu = new ContextMenu(table.getTable(), List.of("Chat", "Unfriend", "Refresh"));

        String[] statusOptions = {"All", "Online", "Offline"};
        JComboBox<String> filterStatus = new JComboBox<>(statusOptions);
        filterStatus.setSelectedIndex(0);
        filterStatus.setFont(new Font("Serif", Font.PLAIN, 14));

        filterStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String status = (String) cb.getSelectedItem();

                ReloadTable.reloadFriendTable(table, status);
            }
        });

        JLabel filterLabel = new JLabel("Filter by Status: ");
        filterLabel.setFont(new Font("Serif", Font.PLAIN, 14));

        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(filterLabel);
        filterPanel.add(filterStatus);

        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("LIST FRIENDS");
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

        gbc.gridx = 1;
        header.add(filterPanel, gbc);

        JMenuItem unfriend = contextMenu.getUnfriend();
        unfriend.addActionListener(new UnfriendAction(table));

        JMenuItem chatButton = contextMenu.getChat();
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userID = UserProfile.getUserProfile().getId();
                int friendID = table.getSelectedData().getId();

                try {
                    int groupID = checkGChatExisting(userID, friendID);
                    EventHandlerService.getInstance().notify(ComponentIdContext.CHAT_SCREEN_ID, "SHOW->" + String.valueOf(groupID)); // if msg is a String is pop up chat
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                mainCard.show(mainContentPanel, "CHAT");
            }
        });

        JMenuItem refresh = contextMenu.getRefresh();
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReloadTable.reloadFriendTable(table, "All");
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

    private int checkGChatExisting(int userID, int friendID) throws Exception {
        GChatService gcservice = GChatService.getInstance();
        UserService userService = UserService.getInstance();
        List<GroupChat> groupChatsOfUser = gcservice.getGChatList(userID);

        for(GroupChat groupChat : groupChatsOfUser){
            if(!groupChat.isGroup()){
                List<GroupChatMember> groupMembers = gcservice.getGroupChatMembers(groupChat.getGroupId());

                if (groupMembers.size() == 2) {
                    GroupChatMember member1 = groupMembers.get(0);
                    GroupChatMember member2 = groupMembers.get(1);

                    if ((member1.getUserId() == userID && member2.getUserId() == friendID) ||
                            (member1.getUserId() == friendID && member2.getUserId() == userID)) {
                        return groupChat.getGroupId();
                    }
                }
            }
        }

        User user = userService.getUserById(userID);
        User friend = userService.getUserById(friendID);

        String[] userName = user.getName().split("\\s+");
        String[] friendName = friend.getName().split("\\s+");

        String groupName = userName[userName.length - 1] + " - " + friendName[friendName.length - 1];

        int newGroupID = gcservice.createEmptyGroup(groupName);

        gcservice.addMember2Group(newGroupID, userID);
        gcservice.addMember2Group(newGroupID, friendID);

        gcservice.updateGroupMemberRole(newGroupID, userID, 1);
        gcservice.updateGroupMemberRole(newGroupID, friendID, 1);

        return newGroupID;
    }

}
