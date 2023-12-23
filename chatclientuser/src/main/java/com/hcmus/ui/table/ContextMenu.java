package com.hcmus.ui.table;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ContextMenu extends JPopupMenu {
    private JMenuItem unfriend;
    private JMenuItem chat;
    private JTable table;
    private List<String> options;
    private JMenuItem filterMenu;

    public ContextMenu(JTable table, List<String> options) {
        this.table = table;
        this.options = options;
        if (options.contains("Unfriend")) {
            unfriend = new JMenuItem("Unfriend");
            add(unfriend);
        }
        if (options.contains("Chat")) {
            chat = new JMenuItem("Chat");
            add(chat);
        }
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showPopupMenu(e);
            }
        });
    }
    private void showPopupMenu(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());

        if (row >= 0 && row < table.getRowCount() && column >= 0 && column < table.getColumnCount()) {
            table.setRowSelectionInterval(row, row);

        } else {
            table.clearSelection();

        }
        show(table, e.getX(), e.getY());
    }

    public int getSelectedRowIndex() {
        return table.getSelectedRow();
    }

    public JMenuItem getUnfriend() {
        return unfriend;
    }

    public void setUnfriend(JMenuItem unfriend) {
        this.unfriend = unfriend;
    }

    public JMenuItem getChat() {
        return chat;
    }

    public void setChat(JMenuItem chat) {
        this.chat = chat;
    }

    public JTable getTable() {
        return table;
    }

    public void setFilterMenu(JMenuItem filterMenu) {
        this.filterMenu = filterMenu;
        add(filterMenu);
    }
}

