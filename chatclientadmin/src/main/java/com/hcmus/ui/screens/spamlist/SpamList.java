package com.hcmus.ui.screens.spamlist;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.spam.SpamReport;
import com.hcmus.services.GChatService;
import com.hcmus.services.SpamService;
import com.hcmus.ui.screens.chatlist.adminlist.AdminListAction;
import com.hcmus.ui.screens.chatlist.memberlist.MemberListAction;
import com.hcmus.ui.screens.spamlist.block.BlockAction;
import com.hcmus.ui.table.ContextMenu;
import com.hcmus.ui.table.DateFilterMenu;
import com.hcmus.ui.table.FilterMenuBuilder;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

            FilterMenuBuilder filterMenuBuilder = new FilterMenuBuilder().setModel(table.getModel()).setSorter(table.getSorter()).setFilterName("Filter by Username");
            JTextField username = new JTextField(10);
            username.setName("Reported Username");
            filterMenuBuilder.setFilterComponents(new JComponent[]{username});
            filterMenuBuilder.setFilterLabels(new JLabel[]{new JLabel("Username")});

            contextMenu = new ContextMenu(table.getTable(), List.of("Block"));
            contextMenu.add(filterMenuBuilder.createFilterMenu());

            filterMenuBuilder.setFilterName("Filter by Date");
            filterMenuBuilder.setFilterLabels(new JLabel[]{new JLabel("Created Time")});
            DateFilterMenu dateFilterMenu = filterMenuBuilder.createDateFilterMenu();
            contextMenu.add(dateFilterMenu);

            ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
            sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
            table.getSorter().setSortKeys(sortKeys);
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
