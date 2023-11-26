package com.hcmus.ui.loginscreens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPassword extends JFrame {
    private JPanel panel;
    private JTextField txtEmail;
    private JButton btnSend;
    private JLabel lblForgotPassword;

    public ForgotPassword() {
        init();
        addListener();
    }

    private void init() {
        setTitle("Forgot password");
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

        lblForgotPassword = new JLabel("Forgot password");
        lblForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        panel.add(lblForgotPassword, gbc);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField(22);
        txtEmail.setMargin(new Insets(10, 5, 10, 5));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setForeground(Color.GRAY);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        panel.add(txtEmail, gbc);

        btnSend = new JButton("Send");
        btnSend.setFont(new Font("Tahoma", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        panel.add(btnSend, gbc);
    }

    private void addListener() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email is empty");
                } else {
                    JOptionPane.showMessageDialog(null, "Send email to " + email);
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {
        new ForgotPassword().setVisible(true);
    }
}
