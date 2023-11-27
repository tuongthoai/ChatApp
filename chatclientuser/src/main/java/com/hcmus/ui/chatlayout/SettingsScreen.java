package com.hcmus.ui.chatlayout;

import com.hcmus.ui.loginscreens.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsScreen extends JPanel {
    private JButton button;
    private JButton button1;
    private ChangePasswordScreen changePasswordScreen;
    public SettingsScreen() {
        setLayout(new GridLayout(2, 1));

        button = new JButton("Change password");
        button1 = new JButton("Logout");
        button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        button1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(button);
        add(button1);

        addListener();
    }

    private void changePassword() {
        changePasswordScreen = new ChangePasswordScreen(this);
        this.setVisible(false);
        this.getParent().add(changePasswordScreen, "changePassword");
        CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
        cardLayout.show(this.getParent(), "changePassword");
    }

    private void logout() {
        // dispose all frame
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
        // open login frame
        Login login = new Login();
        login.setVisible(true);

    }

    private void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }
}
