package com.hcmus.ui.table;

import com.hcmus.helper.VietnameseRowFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterMenu extends JMenu {
    protected List<JComponent> components;
    protected JLabel[] labels;
    protected TableRowSorter<DefaultTableModel> sorter;
    protected DefaultTableModel model;

    protected JPanel panel;

    public FilterMenu(TableRowSorter<DefaultTableModel> sorter, DefaultTableModel model, String filterName, ArrayList<JComponent> components, JLabel[] labels) {
        super(filterName);
        this.model = model;
        this.sorter = sorter;
        this.components = components;
        this.labels = labels;

        initDialog();

        JMenuItem filterItem = new JMenuItem("Filter");
        JMenuItem clearFilterItem = new JMenuItem("Clear Filter");
        add(filterItem);
        add(clearFilterItem);

        filterItem.addActionListener(e -> FilterMenu.this.showFilterDialog());

        clearFilterItem.addActionListener(e -> sorter.setRowFilter(null));
    }

    public void setFilterComponents(JComponent... components) {
        this.components = new ArrayList<>();
        Collections.addAll(this.components, components);
    }

    public void setFilterLabels(JLabel... labels) {
        this.labels = labels;
    }

    public void initDialog() {
        if (components == null || labels == null)
            throw new IllegalStateException("Filter components and labels must be set");
        if (panel == null) {
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        }
        panel.removeAll();

        for (int i = 0; i < components.size(); i++) {
            JPanel row = new JPanel();
            row.add(labels[i]);
            row.add(components.get(i));
            panel.add(row);
        }

        panel.revalidate();
    }

    protected void showFilterDialog() {
        if (panel == null) throw new IllegalStateException("Filter dialog must be initialized");

        int result = JOptionPane.showConfirmDialog(null, panel, "Filter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) applyFilter();
        if (result == JOptionPane.CANCEL_OPTION) sorter.setRowFilter(null);
    }

    protected void applyFilter() {
        List<RowFilter<TableModel, Integer>> filters = new ArrayList<>();

        for (JComponent component : this.components) {
            if (!(component instanceof JTextField)) {
                if (component instanceof JComboBox) {
                    JComboBox comboBox = (JComboBox) component;
                    String columnName = labels[components.indexOf(component)].getText();
                    int columnIndex = getColumnIndex(columnName);
                    String value = (String) comboBox.getSelectedItem();
                    if (!value.equals("All")) {
                        if (value.equals("Online") || value.equals("Blocked")) value = "true";
                        else value = "false";
                        filters.add(new VietnameseRowFilter(columnIndex, value));
                    }
                }
                continue;
            }
            JTextField textField = (JTextField) component;
            String text = textField.getText();
            String columnName = textField.getName();
            if (!text.isEmpty()) {
                int columnIndex = getColumnIndex(columnName);
                filters.add(new VietnameseRowFilter(columnIndex, text));
            }
        }

        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    protected int getColumnIndex(String columnName) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            if (model.getColumnName(i).equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        throw new IllegalStateException("Column name not found");
    }
}
