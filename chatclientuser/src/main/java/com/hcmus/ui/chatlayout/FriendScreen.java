package com.hcmus.ui.chatlayout;

import javax.swing.*;
import java.awt.*;

public class FriendScreen extends JPanel {
    public FriendScreen() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Friend Screen");
        add(label, BorderLayout.CENTER);
    }
}
