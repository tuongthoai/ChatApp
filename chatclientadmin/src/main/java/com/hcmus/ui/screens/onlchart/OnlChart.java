package com.hcmus.ui.screens.onlchart;

import com.hcmus.services.OnlineChartService;
import com.hcmus.ui.chart.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OnlChart extends JPanel {
    private OnlineChartService service;
    private Chart chart;
    public OnlChart() {
        service = new OnlineChartService();
        List<Integer> years = service.getYears();

        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel chooseLabel = new JLabel("Choose the year: ", JLabel.LEFT);
        chooseLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JComboBox<Integer> yearList = new JComboBox<>(years.toArray(new Integer[0]));
        yearList.setSelectedIndex(0);
        yearList.setFont(new Font("Serif", Font.PLAIN, 18));

        chart = createChart(service.getNumberOfOnlineUser((int) yearList.getSelectedItem()), (int) yearList.getSelectedItem());

        yearList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                int year = (int) cb.getSelectedItem();

                removeChart();
                chart = createChart(service.getNumberOfOnlineUser(year), year);
                add(chart, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        headerPanel.add(chooseLabel);
        headerPanel.add(yearList);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(chart, BorderLayout.CENTER);
    }

    private void removeChart() {
        if (chart != null) {
            remove(chart); // Remove the existing chart from the panel
            chart = null; // Set the reference to null
        }
    }

    private Chart createChart(int[] data, int year) {
        List<Integer> values = Arrays.stream(data)
                .boxed()
                .collect(Collectors.toList());

        Chart newChart = new Chart("access", values, String.valueOf(year));
        newChart.setPreferredSize(new Dimension(500, 500));
        return newChart;
    }
}
