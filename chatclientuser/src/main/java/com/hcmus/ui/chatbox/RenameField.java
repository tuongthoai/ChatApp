package com.hcmus.ui.chatbox;

import com.hcmus.ui.chatbox.RenameAction.RenameFieldBtnActionImpl;

import javax.swing.*;
import java.awt.*;

public class RenameField extends JPanel {
    private JTextField textField;
    private JButton renameBtn;

    public RenameField(ChatBox parent) {
        setLayout(new BorderLayout());
        JTextField textField = new JTextField(parent.getChatName());
        textField.setPreferredSize(new Dimension(150, 30));
        textField.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JButton renameBtn = new JButton("Rename");
        renameBtn.setPreferredSize(new Dimension(80, 30));
        renameBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        renameBtn.addActionListener(new RenameFieldBtnActionImpl(parent, this));

        add(textField, BorderLayout.CENTER);
        add(renameBtn, BorderLayout.EAST);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }
}
