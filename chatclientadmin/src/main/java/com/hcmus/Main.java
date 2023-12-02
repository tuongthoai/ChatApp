package com.hcmus;

import com.hcmus.ui.layout.Layout;
import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Layout();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


}