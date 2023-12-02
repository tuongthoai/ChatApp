package com.hcmus.ui.chart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Test {
    Test() {
        JFrame jfrm = new JFrame("Test");
        jfrm.setSize(800, 400); // Adjust the size according to your needs
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Integer> value1 = List.of(5, 10, 15, 8, 2, 4, 1, 4, 6, 18, 4, 1);
        String year1 = "2023";

        List<Integer> value2 = List.of(6, 1, 4, 2, 4, 4, 4, 0, 10, 23, 1, 3);
        String year2 = "2022";

        Chart registerChart = new Chart("register", value1, year1);
        Chart accessChart = new Chart("access", value2, year2);

        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(registerChart);
        container.add(accessChart);

        jfrm.getContentPane().add(container);
        jfrm.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });
    }
}
