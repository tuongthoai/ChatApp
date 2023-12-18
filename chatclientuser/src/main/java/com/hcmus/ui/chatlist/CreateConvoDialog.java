package com.hcmus.ui.chatlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateConvoDialog {
    CreateConvoDialog(List<String> listFriend) {
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel jlTitle = new JLabel("Create a new Group chat", JLabel.CENTER);
        Font font1 = new Font("Serif", Font.BOLD, 24);
        jlTitle.setFont(font1);
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jlTitle, gbc);

        JLabel grNameLabel = new JLabel("Group Name");
        grNameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(grNameLabel, gbc);

        JTextField grNameField = new JTextField(20);
        grNameField.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(grNameField, gbc);

        JLabel searchLabel = new JLabel("Search friend");
        searchLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(searchLabel, gbc);

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                JOptionPane.showMessageDialog(null, "SEARCH " + keyword);
                searchField.setText("");
            }
        });
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(searchButton, gbc);

        for (String username : listFriend) {
            JLabel name = new JLabel(username);
            name.setFont(new Font("Serif", Font.PLAIN, 18));
            gbc.gridx = 1;
            gbc.gridy = gbc.gridy + 1;
            gbc.gridwidth = 1;
            mainPanel.add(name, gbc);

            JButton addButton = new JButton("ADD");
            addButton.setFont(new Font("Serif", Font.PLAIN, 14));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "ADD " + name);
                }
            });

            gbc.gridx = 2;
            gbc.gridy = gbc.gridy++;
            gbc.gridwidth = 1;
            mainPanel.add(addButton, gbc);
        }

        int result = JOptionPane.showConfirmDialog(null, mainPanel, "Create New Group Chat", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}
