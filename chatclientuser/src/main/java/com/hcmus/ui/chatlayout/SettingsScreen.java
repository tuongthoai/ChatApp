package com.hcmus.ui.chatlayout;

import javax.swing.*;
import java.awt.*;

public class SettingsScreen extends JPanel {
    public SettingsScreen() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Settings Screen");
        add(label, BorderLayout.CENTER);
    }
}
