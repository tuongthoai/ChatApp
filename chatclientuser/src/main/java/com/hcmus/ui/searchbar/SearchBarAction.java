package com.hcmus.ui.searchbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBarAction implements ActionListener {
    private String searchText;

    public SearchBarAction() {}
    public SearchBarAction(String searchText) { this.searchText = searchText; }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!searchText.isEmpty()) {
            // perform search
        }
    }
}
