package com.hcmus;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class DatePickerExample implements Runnable {

    private DefaultTableModel model;

    private JFrame frame;

    private JTable table;

    private TableRowSorter<TableModel> sorter;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new DatePickerExample());
    }

    @Override
    public void run() {
        frame = new JFrame("Date Picker Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(createTablePanel(), BorderLayout.CENTER);
        frame.add(createControlPanel(), BorderLayout.AFTER_LINE_ENDS);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBorder(BorderFactory.createEmptyBorder(
                5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblNewLabel = new JLabel("With Date");
        innerPanel.add(lblNewLabel, gbc);

        String[] options = {"ALL", "LAST 1 WEEK", "LAST 1 MONTH",
                "LAST 1 YEAR"};

        gbc.gridy++;
        JComboBox<String> cbx_date = new JComboBox<>();
        cbx_date.setModel(
                new DefaultComboBoxModel<String>(options));
        cbx_date.addItemListener(new DateItemListener(sorter));
        innerPanel.add(cbx_date, gbc);

        gbc.gridy++;
        JLabel lblWithLocaldate = new JLabel("With LocalDate");
        innerPanel.add(lblWithLocaldate, gbc);

        gbc.gridy++;
        JComboBox<String> cbx_localdate = new JComboBox<>();
        cbx_localdate.setModel(
                new DefaultComboBoxModel<String>(options));
        cbx_localdate.addItemListener(new LocalDateItemListener(sorter));
        innerPanel.add(cbx_localdate, gbc);

        panel.add(innerPanel);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        model = new MyTableModel();

        // Columns
        Object[] columns = {"Date", "Local Date"};
        model.setColumnIdentifiers(columns);

        // Rows
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd.MM.yyyy");
        String[] dates = {"01.08.2023", "05.08.2023", "20.08.2023",
                "21.08.2023", "01.09.2023", "15.09.2023",
                "01.10.2023", "15.10.2023", "01.11.2023",
                "01.08.2022", "01.07.2022"};
        Object[] rows = new Object[2];
        for (int i = 0; i < dates.length; i++) {
            try {
                rows[0] = sdf.parse(dates[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rows[1] = LocalDate.parse(dates[i], formatter);
            // print out type of rows[1]
            System.out.println(rows[1].getClass().getName());
            model.addRow(rows);
        }

        table = new JTable(model);
        sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public class MyTableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public Class<?> getColumnClass(int column) {
            switch (column) {
                case 0:
                    return Date.class;
                case 1:
                    return LocalDate.class;
                default:
                    return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }

    public class DateItemListener implements ItemListener {

        private List<RowFilter<Object, Object>> filters =
                new ArrayList<RowFilter<Object, Object>>(2);

        private TableRowSorter<TableModel> sorter;

        public DateItemListener(TableRowSorter<TableModel> sorter) {
            this.sorter = sorter;
        }

        @Override
        public void itemStateChanged(ItemEvent event) {
            filters.clear();
            String selected = event.getItem().toString();
            if (!selected.equals("ALL")) {
                filters.add(RowFilter.dateFilter(
                        ComparisonType.AFTER, calculateAfterDate(
                                selected)));
                filters.add(RowFilter.dateFilter(
                        ComparisonType.BEFORE, new Date()));
            }
            RowFilter<Object, Object> serviceFilter =
                    RowFilter.andFilter(filters);
            sorter.setRowFilter(serviceFilter);
        }

        // Returns the date as wanted.
        private Date calculateAfterDate(String selected) {
            Calendar cal = Calendar.getInstance();

            if (selected.equals("LAST 1 WEEK")) {
                cal.add(Calendar.DAY_OF_MONTH, -7);
                // Make 1 less date so I can get the EQUALS day.
                // Because of ComparisonType.AFTER
                cal.add(Calendar.DAY_OF_MONTH, -1);
            } else if (selected.equals("LAST 1 MONTH")) {
                cal.add(Calendar.MONTH, -1);
                cal.add(Calendar.DAY_OF_MONTH, -1);
            } else if (selected.equals("LAST 1 YEAR")) {
                cal.add(Calendar.YEAR, -1);
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }

            return cal.getTime();
        }

    }

    public class LocalDateItemListener implements ItemListener {

        private TableRowSorter<TableModel> sorter;

        public LocalDateItemListener(TableRowSorter<TableModel> sorter) {
            this.sorter = sorter;
        }

        @Override
        public void itemStateChanged(ItemEvent event) {
            String selected = event.getItem().toString();
            LocalDate afterDate = calculateAfterDate(selected);

            sorter.setRowFilter(null);

            RowFilter<TableModel, Integer> localDateFilter =
                    new RowFilter<TableModel, Integer>() {
                        @Override
                        public boolean include(Entry<? extends TableModel,
                                ? extends Integer> entry) {
                            TableModel model = entry.getModel();
                            LocalDate date = (LocalDate) model.getValueAt(
                                    entry.getIdentifier(), 1);
                            return date.isAfter(afterDate) &&
                                    date.isBefore(LocalDate.now());
                        }
                    };

            if (!selected.equals("ALL")) {
                sorter.setRowFilter(localDateFilter);
            }
        }

        private LocalDate calculateAfterDate(String selected) {
            LocalDate date = LocalDate.now();

            if (selected.equals("LAST 1 WEEK")) {
                date = date.plusDays(-7);
                date = date.plusDays(-1);
            } else if (selected.equals("LAST 1 MONTH")) {
                date = date.plusMonths(-1);
                date = date.plusDays(-1);
            } else if (selected.equals("LAST 1 YEAR")) {
                date = date.plusYears(-1);
                date = date.plusDays(-1);
            }

            return date;
        }

    }

}
