package com.hcmus.ui.chatlayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordAction implements ActionListener {
    private ChangePasswordScreen changePasswordScreen;

    public ChangePasswordAction(ChangePasswordScreen changePasswordScreen) {
        this.changePasswordScreen = changePasswordScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Change password");
        JOptionPane.showMessageDialog(changePasswordScreen, "Change password");
        changePasswordScreen.setVisible(false);
        changePasswordScreen.getParent().setVisible(true);
    }
}
