package com.hcmus.ui.chatlayout;

import com.hcmus.services.AuthService;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordScreen extends JPanel {
    private JButton changeButton;
    private JTextField oldPassword;
    private JTextField newPassword;
    private JTextField confirmPassword;
    private JPanel parent;

    public ChangePasswordScreen(JPanel parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        JLabel changePasswordLabel = new JLabel("Change Password");
        changePasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        JLabel oldPasswordLabel = new JLabel("Old Password");
        JLabel newPasswordLabel = new JLabel("New Password");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        changeButton = new JButton("Change");
        oldPassword = new JTextField(20);
        newPassword = new JTextField(20);
        confirmPassword = new JTextField(20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(changePasswordLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(oldPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        oldPassword.setMargin(new Insets(5, 5, 5, 0));
        add(oldPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        newPassword.setMargin(new Insets(5, 5, 5, 0));
        add(newPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        confirmPassword.setMargin(new Insets(5, 5, 5, 0));
        add(confirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(changeButton, gbc);

        addListener();
    }

    private void changePassword() {
        JOptionPane.showMessageDialog(this, "Change password");
        this.setVisible(false);
        parent.setVisible(true);
    }

    private void addListener() {
        changeButton.addActionListener(new ChangePasswordAction(this));
    }
    public String getOldPassword() {
        return oldPassword.getText();
    }

    public String getNewPassword() {
        return newPassword.getText();
    }

    public String getConfirmPassword() {
        return confirmPassword.getText();
    }
}
