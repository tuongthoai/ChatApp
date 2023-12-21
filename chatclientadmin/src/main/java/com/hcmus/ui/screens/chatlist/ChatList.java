package com.hcmus.ui.screens.chatlist;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.services.GChatService;
import com.hcmus.ui.screens.chatlist.adminlist.AdminListAction;
import com.hcmus.ui.screens.chatlist.memberlist.MemberListAction;
import com.hcmus.ui.table.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatList extends JPanel {
    private Table<GroupChat> table;
    private ContextMenu contextMenu;
    private GChatService service;
    private SearchBar searchBar;
    private FilterMenu filterMenu;

    public ChatList() {
        try {
            service = new GChatService();
            List<GroupChat> data = service.getAllGChats();
            List<String> columnNames = GroupChat.getColumnNames();

            table = new Table<GroupChat>(data, columnNames);
            contextMenu = new ContextMenu(table.getTable(), List.of("Detail", "Member List", "Admin List"));
            searchBar = new SearchBar(table.getSorter());

            JTextField groupName = new JTextField(10);
            groupName.setName("Group Name");
            FilterMenuBuilder filterMenuBuilder = new FilterMenuBuilder();
            filterMenuBuilder.setSorter(table.getSorter());
            filterMenuBuilder.setModel(table.getModel());
            filterMenuBuilder.setFilterName("Filter");
            filterMenuBuilder.setFilterComponents(new JComponent[]{groupName});
            filterMenuBuilder.setFilterLabels(new JLabel[]{new JLabel("Group Name")});
            filterMenu = filterMenuBuilder.createFilterMenu();

            // Add action listener for each menu item
            JMenuItem memberListItem = contextMenu.getMemListItem();
            memberListItem.addActionListener(new MemberListAction(table));

            JMenuItem adminListItem = contextMenu.getAdminListItem();
            adminListItem.addActionListener(new AdminListAction(table));

            contextMenu.setFilterMenu(filterMenu);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
