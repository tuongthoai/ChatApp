package com.hcmus.ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NumberFilterMenu extends FilterMenu {
    private ArrayList<JComboBox<String>> comboBoxes;
    String [] operators = {"Equal", "Larger than", "Smaller than"};
    public NumberFilterMenu(TableRowSorter<DefaultTableModel> sorter, DefaultTableModel model, String filterName, ArrayList<JComponent> components, JLabel[] labels) {
        super(sorter, model, filterName, components, labels);
    }

    @Override
    public void setFilterComponents(JComponent... components) {
        super.setFilterComponents(components);
        comboBoxes.clear();
    }

    @Override
    public void initDialog() {
        if (components == null || labels == null)
            throw new IllegalStateException("Filter components and labels must be set");
        if (panel == null) {
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        }
        if (comboBoxes == null) {
            comboBoxes = new ArrayList<>();
        } else {
            System.out.println("Clearing combo boxes");
            comboBoxes.clear();
        }
        panel.removeAll();

        for (int i = 0; i < components.size(); i++) {
            JPanel row = new JPanel();
            row.add(labels[i]);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Equal", "Larger than", "Smaller than"});
            comboBox.setEditable(false);
            row.add(comboBox);
            row.add(components.get(i));
            comboBoxes.add(comboBox);
            panel.add(row);
            panel.add(Box.createVerticalStrut(10));
        }

        panel.revalidate();
    }

    @Override
    protected void applyFilter(){
        List<RowFilter<TableModel, Integer>> filters = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            String name = components.get(i).getName();
            String value = ((JTextField) components.get(i)).getText();
            String selectedItem = (String) comboBoxes.get(i).getSelectedItem();
            System.out.println(name + " " + value + " " + selectedItem);
            int columnIndex = super.getColumnIndex(name);
            if (!value.isEmpty()) {
                switch (Objects.requireNonNull(selectedItem)) {
                    case "Equal":
                        filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, Integer.parseInt(value), columnIndex));
                        break;
                    case "Larger than":
                        filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, Integer.parseInt(value), columnIndex));
                        break;
                    case "Smaller than":
                        filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, Integer.parseInt(value), columnIndex));
                        break;
                }
            }

            ((JTextField) components.get(i)).setText("");
        }

        sorter.setRowFilter(RowFilter.andFilter(filters));
    }
}
