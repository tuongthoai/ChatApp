package com.hcmus.ui.table;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class ContextMenu extends JPopupMenu {
    private JMenuItem editItem;
    private JMenuItem deleteItem;
    private JTable table;

    private Connection conn;
    public ContextMenu(JTable table) {
        this.table = table;
        editItem = new JMenuItem("Edit");
        deleteItem = new JMenuItem("Delete");
        add(editItem);
        add(deleteItem);
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
        } else {
            table.clearSelection();
            editItem.setEnabled(false);
            deleteItem.setEnabled(false);
        }
        show(table, e.getX(), e.getY());
    }
}

