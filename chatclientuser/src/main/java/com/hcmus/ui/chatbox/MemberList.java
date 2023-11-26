package com.hcmus.ui.chatbox;

import com.hcmus.ui.chatlist.ChatList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MemberList extends JPanel {
    private JScrollPane mainPanel;

    public MemberList(List<String> members, List<String> roles) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < 10; i++) {
            gbc.gridy = i * 2; // Adjusting gridy to skip a row for JSeparator
            panel.add(new MemberCard(members.get(0), roles.get(0)), gbc);

            if (i < 9) {
                gbc.gridy = i * 2 + 1;
                gbc.weightx = 1.0; // Make the separator expand horizontally
                panel.add(new JSeparator(), gbc);
                gbc.weightx = 0.0; // Reset weightx
            }
        }

        mainPanel = new JScrollPane(panel);
        mainPanel.setPreferredSize(new Dimension(200, 400));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(mainPanel, gbc);
    }

    private class MemberCard extends JPanel {
        private final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 12);

        public MemberCard(String name, String role) {
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());

            JPanel subPanel = new JPanel(new GridBagLayout());
            subPanel.setBackground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(7, 7, 7, 7);

            // Name label
            JLabel nameLabel = createLabel(name.toUpperCase());
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            subPanel.add(nameLabel, gbc);

            // Role label
            JLabel roleLabel = createLabel(role);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            subPanel.add(roleLabel, gbc);

            // Button panel
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            buttonPanel.setBackground(Color.WHITE);

            // Remove button
            JButton removeBtn = createButton("Remove");
            buttonPanel.add(removeBtn);

            // Change role button
            JButton changeRoleBtn = createButton("Change");
            buttonPanel.add(changeRoleBtn);

            // Add the button panel to subPanel
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(0, 20, 0, 0);
            subPanel.add(buttonPanel, gbc);

            subPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            add(subPanel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(LABEL_FONT);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            return label;
        }

        private JButton createButton(String text) {
            JButton button = new JButton(text);
            button.setFont(LABEL_FONT);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            return button;
        }
    }
}
