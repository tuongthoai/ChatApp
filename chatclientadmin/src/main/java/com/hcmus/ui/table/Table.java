package com.hcmus.ui.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;
import java.util.Date;

public class Table<T> extends JScrollPane {
    private final List<String> columnNames;
    private List<T> data;
    private final JTable table;

    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;

    public Table(List<T> data, List<String> columnNames) throws SQLException {
        this.data = data;
        this.columnNames = columnNames;
        this.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        model = new DefaultTableModel(new Vector<>(columnNames), 0);
        sorter = new TableRowSorter<DefaultTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setFillsViewportHeight(true);
        setViewportView(table);
        updateTable();
    }

    public void updateTable() {
        model.setRowCount(0);

        for (T item : data) {
            Vector<Object> row = new Vector<>();
            for (String columnName : columnNames) {
                Field field = getFieldByName(item.getClass(), columnName);
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(item);
                        if (value instanceof Long) {
                            if (columnName.toLowerCase().trim().contains("time")) {
                                value = UnixTimestampConverter.unix2DateTime((long) value);
                            }
                            else {
                                Instant instant = Instant.ofEpochSecond((Long) value);
                                value = LocalDate.ofInstant(instant, ZoneId.systemDefault());
                            }
                        }
                        row.add(value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Field " + columnName + " not found");
                    row.add(null);
                }
            }
            model.addRow(row);
        }

        resizeColumnWidth();

    }

    private Field getFieldByName(Class<?> clazz, String fieldName) {
        if (fieldName.equals("Disconnect Time")) {
            fieldName = "dc Time";
        }
        boolean hasSpace = fieldName.contains(" ");
        if (hasSpace) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            fieldName = fieldName.replaceAll("\\s", "");
        }
        else {
            fieldName = fieldName.toLowerCase();
        }
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private boolean ignoreCaseContains(String src, String keyword) {
        return src.toLowerCase().contains(keyword.toLowerCase());
    }

    public void updateData(List<T> data) {
        this.data = data;
    }


    public void resizeColumnWidth() {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 20; // Min width

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);

            table.setRowHeight(30);
        }
    }

    public int getSelectedRowIndex() {
        return table.getSelectedRow();
    }

    public T getSelectedData() {
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            return data.get(selectedRowIndex);
        }

        return null;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
}
