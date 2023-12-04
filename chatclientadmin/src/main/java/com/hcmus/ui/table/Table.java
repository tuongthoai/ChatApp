package com.hcmus.ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class Table<T> extends JScrollPane {
    private final List<String> columnNames;
    private List<T> data;
    private final JTable table;

    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;

    public Table(List<T> data, List<String> columnNames) throws SQLException {
        this.data = data;
        this.columnNames = columnNames;
        model = new DefaultTableModel(new Vector<>(columnNames), 0);
        sorter = new TableRowSorter<>(model);
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
                            value = UnixTimestampConverter.unix2DateTime((long) value).toString();
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

    }

    private Field getFieldByName(Class<?> clazz, String fieldName) {
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


//    public void filterTable(String keyword) {
//        Predicate<Student> matchesKeyword = student ->
//                keyword.isEmpty() ||
//                        isNumeric(keyword) && student.getId() == Integer.parseInt(keyword) ||
//                        ignoreCaseContains(student.getFirstName(), keyword) ||
//                        ignoreCaseContains(student.getLastName(), keyword) ||
//                        ignoreCaseContains(student.getAddress(), keyword) ||
//                        ignoreCaseContains(student.getDob().toString(), keyword);
//        ArrayList<Student> filteredStudents = new ArrayList<>(students.stream()
//                .filter(matchesKeyword)
//                .toList());
//
//        model.setRowCount(0);
//        filteredStudents.forEach(student -> {
//            model.addRow(new Object[]{
//                    student.getId(),
//                    student.getFirstName(),
//                    student.getLastName(),
//                    student.getDob(),
//                    student.getAddress()
//            });
//        });
//    }



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
