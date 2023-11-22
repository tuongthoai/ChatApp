package com.hcmus.ui.searchbar;

import javax.swing.*;
import java.awt.*;
public class SearchBar extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    public SearchBar() {
        setLayout(new BorderLayout());
        searchField = new JTextField(30);
        searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchButton.addActionListener(new SearchBarAction("Keyword"));
        add(searchButton, BorderLayout.EAST);
        add(searchField, BorderLayout.CENTER);
        setPreferredSize(new Dimension(200, 35));
        setBorder(BorderFactory.createEmptyBorder(3, 2, 5, 1));
    }
}
