package com.hcmus.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Register extends JFrame {
    private JPanel panel;
    private JTextField txtEmail;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnRegister;

    private JLabel lblRegister;

    public Register() {
        init();
        addListener();
    }

    private void init(){
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setBounds(100, 100, 800, 600);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        lblRegister = new JLabel("Register new account");
        lblRegister.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        panel.add(lblRegister, gbc);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField(22);
        txtEmail.setMargin(new Insets(10, 5, 10, 5));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setForeground(Color.GRAY);
        txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        panel.add(txtEmail, gbc);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(lblUsername, gbc);

        txtUsername = new JTextField(22);
        txtUsername.setMargin(new Insets(10, 5, 10, 5));
        txtUsername.setBackground(Color.WHITE);
        txtUsername.setForeground(Color.GRAY);
        txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        panel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(22);
        txtPassword.setMargin(new Insets(10, 5, 10, 5));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setForeground(Color.GRAY);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 3;
        gbc.gridy = 3;
        panel.add(txtPassword, gbc);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(lblConfirmPassword, gbc);

        txtConfirmPassword = new JPasswordField(22);
        txtConfirmPassword.setMargin(new Insets(10, 5, 10, 5));
        txtConfirmPassword.setBackground(Color.WHITE);
        txtConfirmPassword.setForeground(Color.GRAY);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 3;
        gbc.gridy = 4;
        panel.add(txtConfirmPassword, gbc);

        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        panel.add(btnRegister, gbc);
    }

    private void addListener() {
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String username = txtUsername.getText();
                String password = String.valueOf(txtPassword.getPassword());
                String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());
                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Password and confirm password are not matched", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Register successfully");
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Register().setVisible(true);
        });
    }
}
