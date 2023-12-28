package com.hcmus.ui.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.RangeType;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

public class BarChartTemplate{
    private String title;
    private List<Integer> values;
    private List<String> columns;

    public BarChartTemplate(String title, List<Integer> values, List<String> columns) {
        this.title = title;
        this.values = values;
        this.columns = columns;
    }

    public ChartPanel createBarChart() {
        // Create a dataset
        CategoryDataset dataset = createDataset(this.values, this.columns);

        // Create a bar chart based on the dataset
        JFreeChart chart = ChartFactory.createBarChart(
                this.title,
                "Months",
                "Number of Users",
                dataset
        );

        customizeRenderer(chart);
        return new ChartPanel(chart);
    }

    private CategoryDataset createDataset(List<Integer> values, List<String> columnKeys) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add data to the dataset
        for(int i = 0; i < values.size(); i++){
            dataset.addValue(values.get(i), "", columnKeys.get(i));
        }

        return dataset;
    }

    private void customizeRenderer(JFreeChart chart) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Show data labels on top of the columns
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);

        // Customize data label position
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER
        ));

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRangeType(RangeType.POSITIVE);
        yAxis.setTickUnit(new NumberTickUnit(1));
    }
}
