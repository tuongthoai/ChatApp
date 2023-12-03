package com.hcmus.ui.table;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DetailList<T> extends JDialog {
    private List<T> data;
    private List<String> columnNames;
    private Table<T> table;

    public DetailList(List<T> data, List<String> columnNames, String title) throws SQLException {
        this.data = data;
        this.columnNames = columnNames;
        this.table = new Table<T>(data, columnNames);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(title);
        add(this.table, BorderLayout.CENTER);

        setVisible(true);
    }
}
