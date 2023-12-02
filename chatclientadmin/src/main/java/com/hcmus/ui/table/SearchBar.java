package com.hcmus.ui.table;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class SearchBar extends JPanel {
    private final JTextField searchField;
    private final TableRowSorter<DefaultTableModel> sorter;
    public SearchBar(TableRowSorter<DefaultTableModel> model) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(50);
        sorter = model;
        add(searchLabel);
        add(searchField);
        addSearchListener();
    }

    private void addSearchListener() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if (text.trim().isEmpty()) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if (text.trim().isEmpty()) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if (text.trim().isEmpty()) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
