package com.hcmus.ui.screens.userlist.friendlist;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;

public class FriendListDialog extends JDialog {
    private JPanel title;
    private JPanel contentPanel;
    private String username;
    private Table<User> tablePanel;

    public FriendListDialog() {
    }

    public FriendListDialog(Table<User> tablePanel ,JPanel contentPanel, String username) {
        this.tablePanel = tablePanel;
        this.username = username;
        this.contentPanel = contentPanel;
        contentPanel.setPreferredSize(new Dimension(500, 150));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setPreferredSize(new Dimension(500, 150));
        setLayout(new BorderLayout());
        title = new JPanel();
        JLabel titleLabel = new JLabel("Friend List" + " - " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        title.add(titleLabel);

        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(null);
    }
}
