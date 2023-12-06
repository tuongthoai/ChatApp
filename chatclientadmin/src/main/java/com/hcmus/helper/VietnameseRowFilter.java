package com.hcmus.helper;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseRowFilter extends RowFilter<TableModel, Integer> {
    private final int columnIndex;
    private String filterText;
    public VietnameseRowFilter(int columnIndex, String filterText) {
        this.columnIndex = columnIndex;
        this.filterText = filterText;
    }
    @Override
    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
        Object value = entry.getValue(columnIndex);
        if (value != null) {
            String cellText = value.toString().toLowerCase();
            cellText = normalize(cellText);
            filterText = normalize(filterText);

            return cellText.contains(filterText.toLowerCase());
        }
        return false;
    }

    private String normalize (String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    public static <M extends TableModel> void applyFilter(TableRowSorter<M> sorter, int columnIndex, String filterText) {
        sorter.setRowFilter(new VietnameseRowFilter(columnIndex, filterText));
    }
}
