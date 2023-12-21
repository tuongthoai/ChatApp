package com.hcmus.ui.table;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class DateRangeSelector extends JPanel {

    private final JDatePickerImpl startDatePicker;
    private final JDatePickerImpl endDatePicker;
    private JButton searchButton;

    public DateRangeSelector() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        searchButton = new JButton("Search");

        UtilDateModel startDateModel = new UtilDateModel();
        UtilDateModel endDateModel = new UtilDateModel();

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, properties);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, properties);

        startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

        JPanel startDatePane = new JPanel();
        startDatePane.add(new JLabel("Start Date:"));
        startDatePane.add(startDatePicker);
        JPanel endDatePane = new JPanel();
        endDatePane.add(new JLabel("End Date:"));
        endDatePane.add(endDatePicker);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startDatePane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(endDatePane, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(searchButton, gbc);
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String pattern = "dd/MM/yyyy";
        private final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            try {
                return dateFormat.parseObject(text);
            } catch (ParseException e) {
                // Handle the exception or return null if parsing fails
                e.printStackTrace();
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

    public JButton getSearchButton() {
        return searchButton;
    }
    public Date getStartDate() {
        return (Date) startDatePicker.getModel().getValue();
    }
    public Date getEndDate() {
        return (Date) endDatePicker.getModel().getValue();
    }
}
