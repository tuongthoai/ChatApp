package com.hcmus.ui.friendscreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainButtonAction implements ActionListener {
    private String text;
    private CardLayout cardLayout;
    private JPanel cardContainPanel;

    public MainButtonAction(String text, CardLayout cardLayout, JPanel cardContainPanel) {
        this.text = text;
        this.cardLayout = cardLayout;
        this.cardContainPanel = cardContainPanel;
    }

    public void actionPerformed(ActionEvent e) {
        cardLayout.show(cardContainPanel, this.text);
    }
}
