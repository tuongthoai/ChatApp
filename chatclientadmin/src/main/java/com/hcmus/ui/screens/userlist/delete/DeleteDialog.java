package com.hcmus.ui.screens.userlist.delete;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.ReloadTable;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DeleteDialog extends JDialog {
    private JButton buttonOK;
    private JButton buttonCancel;
    private Table<User> table;
    private JPanel label;
    private JPanel contentPanel;
    private User selectedUser;

    public DeleteDialog() {
    }

    public DeleteDialog(Table<User> table) {
        this.table = table;
        this.selectedUser = table.getSelectedData();

        contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setPreferredSize(new Dimension(500, 300));
        contentPanel.setLayout(new GridBagLayout());

        initializeButtons();
        setupLayout();
        setPreferredSize(new Dimension(350, 150));
        setTitle("Delete item");

        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void initializeButtons() {
        buttonOK = new JButton("Yes");
        buttonCancel = new JButton("No");
        buttonOK.addActionListener(this::onOK);
        buttonCancel.addActionListener(this::onCancel);

        label = new JPanel();
        label.setLayout(new BorderLayout());
        label.add(new JLabel("Are you sure you want to delete " + (selectedUser.getName())), BorderLayout.CENTER);
    }

    public void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 15, 10, 15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(label, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(buttonOK);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(buttonCancel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(buttonPanel, gbc);
    }

    private void onOK(ActionEvent e) {
        int userId = selectedUser.getId();
        try {
            UserService service = new UserService();
            service.removeUser(userId);
            JOptionPane.showMessageDialog(this, "Remove user successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            ReloadTable.reloadUserTable(table);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Remove user failed", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
    }

    private void onCancel(ActionEvent e) {
        dispose();
    }
}
