package com.hcmus.ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Collections;

public class FilterMenuBuilder {
    protected TableRowSorter<DefaultTableModel> sorter = null;
    protected DefaultTableModel model = null;
    protected String filterName = null;
    protected ArrayList<JComponent> components = null;
    protected JLabel[] labels = null;
    public FilterMenuBuilder setSorter(TableRowSorter<DefaultTableModel> sorter) {
        this.sorter = sorter;
        return this;
    }

    public FilterMenuBuilder setModel(DefaultTableModel model) {
        this.model = model;
        return this;
    }

    public FilterMenuBuilder setFilterName(String filterName) {
        this.filterName = filterName;
        return this;
    }

    public FilterMenuBuilder setFilterComponents(JComponent... components) {
        this.components = new ArrayList<>();
        Collections.addAll(this.components, components);
        return this;
    }

    public FilterMenuBuilder setFilterLabels(JLabel... labels) {
        this.labels = labels;
        return this;
    }

    public FilterMenu createFilterMenu() {
        return new FilterMenu(sorter, model, filterName, components, labels);
    }

    public NumberFilterMenu createNumberFilterMenu() {
        return new NumberFilterMenu(sorter, model, filterName, components, labels);
    }
    public DateFilterMenu createDateFilterMenu() {
        return new DateFilterMenu(sorter, model, filterName, labels);
    }
}