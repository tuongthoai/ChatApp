package com.hcmus.ui.table;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ContextMenu extends JPopupMenu {
    private JMenuItem addItem;
    private JMenuItem editItem;
    private JMenuItem deleteItem;
    private JMenuItem blockItem;
    private JMenu detailItem;
    private JMenuItem loginHisItem;
    private JMenuItem friendListItem;
    private JMenuItem memListItem;
    private JMenuItem adminListItem;
    private JTable table;
    private List<String> options;

    public ContextMenu(JTable table, List<String> options) {
        this.table = table;
        this.options = options;
        if (options.contains("Add")) {
            addItem = new JMenuItem("Add");
            add(addItem);
        }
        if (options.contains("Edit")) {
            editItem = new JMenuItem("Edit");
            add(editItem);
        }
        if (options.contains("Delete")) {
            deleteItem = new JMenuItem("Delete");
            add(deleteItem);
        }
        if (options.contains("Block")) {
            blockItem = new JMenuItem("Block/Unblock");
            add(blockItem);
        }
        if (options.contains("Detail")) {
            detailItem = new JMenu("Detail");
            if (options.contains("Login History")) {
                loginHisItem = new JMenuItem("Login History");
                detailItem.add(loginHisItem);
            }
            if (options.contains("Friend List")) {
                friendListItem = new JMenuItem("Friend List");
                detailItem.add(friendListItem);
            }
            if (options.contains("Member List")) {
                memListItem = new JMenuItem("Member List");
                detailItem.add(memListItem);
            }
            if (options.contains("Admin List")) {
                adminListItem = new JMenuItem("Admin List");
                detailItem.add(adminListItem);
            }
            add(detailItem);
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

    public JMenuItem getEditItem() {
        int rowIndex = table.getSelectedRow();
        editItem.setEnabled(rowIndex != -1);
        return editItem;
    }

    public JMenuItem getDeleteItem() {
        int rowIndex = table.getSelectedRow();
        deleteItem.setEnabled(rowIndex != -1);
        return deleteItem;
    }

    private void showPopupMenu(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());

        if (row >= 0 && row < table.getRowCount() && column >= 0 && column < table.getColumnCount()) {
            table.setRowSelectionInterval(row, row);
            editItem.setEnabled(true);
            deleteItem.setEnabled(true);
            blockItem.setEnabled(true);
            detailItem.setEnabled(true);
        } else {
            table.clearSelection();
            editItem.setEnabled(false);
            deleteItem.setEnabled(false);
            blockItem.setEnabled(false);
            detailItem.setEnabled(false);
        }
        show(table, e.getX(), e.getY());
    }

    public int getSelectedRowIndex() {
        return table.getSelectedRow();
    }

    public JMenuItem getAddItem() {
        return addItem;
    }
    public JMenuItem getBlockItem() {
        return blockItem;
    }

    public JMenu getDetailItem() {
        return detailItem;
    }

    public JMenuItem getLoginHisItem() {
        return loginHisItem;
    }

    public JMenuItem getFriendListItem() {
        return friendListItem;
    }

    public JMenuItem getMemListItem() {
        return memListItem;
    }

    public JMenuItem getAdminListItem() {
        return adminListItem;
    }

    public JTable getTable() {
        return table;
    }
}

