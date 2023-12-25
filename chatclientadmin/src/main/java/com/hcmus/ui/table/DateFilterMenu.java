package com.hcmus.ui.table;

import kotlin.Pair;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateFilterMenu extends FilterMenu{
    private ArrayList<JDatePickerImpl> startDatePickers;
    private ArrayList<JDatePickerImpl> endDatePickers;
    public DateFilterMenu(TableRowSorter<DefaultTableModel> sorter, DefaultTableModel model, String filterName, JLabel[] labels) {
        super(sorter, model, filterName, null, labels);
    }

    @Override
    public void initDialog() {
        if (labels == null)
            throw new IllegalStateException("Filter components and labels must be set");
        if (panel == null) {
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        }
        if (startDatePickers == null) {
            startDatePickers = new ArrayList<>();
        } else {
            System.out.println("Clearing start date pickers");
            startDatePickers.clear();
        }
        if (endDatePickers == null) {
            endDatePickers = new ArrayList<>();
        } else {
            System.out.println("Clearing end date pickers");
            endDatePickers.clear();
        }
        panel.removeAll();

        for (JLabel label : labels) {
            JPanel row = new JPanel();
            row.add(label);
            JDatePickerImpl startDatePicker = initDatePickers();
            JDatePickerImpl endDatePicker = initDatePickers();
            startDatePickers.add(startDatePicker);
            endDatePickers.add(endDatePicker);
            row.add(startDatePicker);
            row.add(endDatePicker);


            panel.add(row);
            panel.add(Box.createVerticalStrut(10));
        }

        panel.revalidate();
    }
    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String pattern = "yyyy/MM/dd";
        private final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            try {
                return dateFormat.parseObject(text);
            } catch (ParseException e) {
                // Handle the exception or return null if parsing fails
                e.printStackTrace(System.err);
                return null;
            }
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value instanceof Date) {
                return dateFormat.format((Date) value);
            } else if (value instanceof Calendar) {
                return dateFormat.format(((Calendar) value).getTime());
            }
            return "";
        }
    }
    private JDatePickerImpl initDatePickers(){
        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    @Override
    protected void applyFilter(){
        List<RowFilter<TableModel, Integer>> filters = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            String name = labels[i].getText();
            String startDateString = startDatePickers.get(i).getJFormattedTextField().getText();
            String endDateString = endDatePickers.get(i).getJFormattedTextField().getText();
            if (startDateString.isEmpty() && endDateString.isEmpty()) {
                continue;
            }

            System.out.println(name + " " + startDateString + " " + endDateString);

            int columnIndex = super.getColumnIndex(name);
            RowFilter<TableModel, Integer> localDateFilter = new RowFilter<TableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                    TableModel model = entry.getModel();
                    LocalDateTime date = (LocalDateTime) model.getValueAt(entry.getIdentifier(), columnIndex);
                    LocalDateTime startDate = null;
                    LocalDateTime endDate = null;
                    if (!startDateString.isEmpty()) {
                        startDate = LocalDateTime.parse(startDateString + " 00:00:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    }
                    if (!endDateString.isEmpty()) {
                        endDate = LocalDateTime.parse(endDateString + " 23:59:59", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    }
                    return date.isAfter(startDate) && date.isBefore(endDate);
                }
            };

            sorter.setRowFilter(localDateFilter);
        }
    }
}
