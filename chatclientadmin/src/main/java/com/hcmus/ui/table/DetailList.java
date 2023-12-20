package com.hcmus.ui.table;

import com.hcmus.entities.user.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DetailList<T> extends JDialog {
    public DetailList() {}

    public DetailList(Table<?> tablePanel ,Table<T> contentPanel, String listTitle) throws SQLException {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1, 1));
        content.add(contentPanel);

        setPreferredSize(new Dimension(800, 400));
        setLayout(new BorderLayout());
        JPanel title = new JPanel();
        JLabel titleLabel = new JLabel(listTitle);
        System.out.println(listTitle);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        title.add(titleLabel);

        add(title, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(tablePanel);
    }
}
