package com.hcmus.ui.screens.chatlist;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.services.GChatService;
import com.hcmus.ui.screens.chatlist.adminlist.AdminListAction;
import com.hcmus.ui.screens.chatlist.memberlist.MemberListAction;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatList extends JPanel {
    private Table<GroupChat> table;
    private ContextMenu contextMenu;
    private GChatService service;

    public ChatList() {
        try {
            service = new GChatService();
            List<GroupChat> data = service.getAllGChats();
            List<String> columnNames = GroupChat.getColumnNames();

            table = new Table<GroupChat>(data, columnNames);
            contextMenu = new ContextMenu(table.getTable(), List.of("Detail", "Member List", "Admin List"));

            // Add action listener for each menu item
            JMenuItem memberListItem = contextMenu.getMemListItem();
            memberListItem.addActionListener(new MemberListAction(table));

            JMenuItem adminListItem = contextMenu.getAdminListItem();
            adminListItem.addActionListener(new AdminListAction(table));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
    }
}
