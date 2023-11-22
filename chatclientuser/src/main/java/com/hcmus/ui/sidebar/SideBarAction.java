package com.hcmus.ui.sidebar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideBarAction implements ActionListener {
    private String text;

    public SideBarAction() {
    }

    public SideBarAction(String text) {
        this.text = text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, text + " Clicked!");
    }
}
