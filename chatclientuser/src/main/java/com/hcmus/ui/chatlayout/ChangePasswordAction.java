package com.hcmus.ui.chatlayout;

import com.hcmus.UserProfile;
import com.hcmus.models.User;
import com.hcmus.services.AuthService;
import com.hcmus.services.UserService;

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
        User currentUser = UserProfile.getUserProfile();
        AuthService authService = AuthService.getInstance();
        UserService userService = UserService.getInstance();
        int userId = -1;
        try {
            userId = authService.login(currentUser.getUsername(), changePasswordScreen.getOldPassword());
            if (userId == -1) {
                JOptionPane.showMessageDialog(null, "Invalid old password!");
                return;
            }
            if (!changePasswordScreen.getNewPassword().equals(changePasswordScreen.getConfirmPassword())) {
                JOptionPane.showMessageDialog(null, "New password and confirm password are not matched!");
                return;
            }

            currentUser.setPassword(changePasswordScreen.getNewPassword());
            userService.updateUser(currentUser);
            JOptionPane.showMessageDialog(null, "Your password has been updated!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        changePasswordScreen.setVisible(false);
        changePasswordScreen.getParent().setVisible(true);
    }
}
