package com.hcmus.ui.screens.spamlist;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.spam.SpamReport;
import com.hcmus.services.GChatService;
import com.hcmus.services.SpamService;
import com.hcmus.ui.screens.chatlist.adminlist.AdminListAction;
import com.hcmus.ui.screens.chatlist.memberlist.MemberListAction;
import com.hcmus.ui.screens.spamlist.block.BlockAction;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SpamList extends JPanel {
    private Table<SpamReport> table;
    private ContextMenu contextMenu;
    private SpamService service;

    public SpamList() {
        try {
            service = new SpamService();
            List<SpamReport> data = service.getAllSpamReports();
            List<String> columnNames = SpamReport.getColumnNames();

            table = new Table<SpamReport>(data, columnNames);
            contextMenu = new ContextMenu(table.getTable(), List.of("Block"));

            // Add action listener for each menu item
            JMenuItem blockItem = contextMenu.getBlockItem();
            blockItem.addActionListener(new BlockAction(table));

        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
    }
}
