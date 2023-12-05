package com.hcmus.ui.chart;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Chart extends JPanel {
    public Chart(String type, List<Integer> numberOfUsers, String year) {
        String title = (type.equals("access")) ? "The number of users accessing app in " : "The number of new users who registered in ";
        title = title + year;

        List<String> columnKeys = List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        BarChartTemplate barChartTemplate = new BarChartTemplate(title, numberOfUsers, columnKeys);

        // Create a chart panel using BarChartTemplate
        ChartPanel chartPanel = barChartTemplate.createBarChart();

        // Set the layout and add the chart panel to the frame
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
}
