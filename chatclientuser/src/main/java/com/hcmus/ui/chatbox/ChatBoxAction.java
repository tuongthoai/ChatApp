package com.hcmus.ui.chatbox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBoxAction implements ActionListener {
    private String text;
    private ChatBox chatBox;
    private JTextField textField;


    public ChatBoxAction(String text) {
        this.text = text;
    }

    public ChatBoxAction(String text, ChatBox chatBox, JTextField textField) {
        this.text = text;
        this.chatBox = chatBox;
        this.textField = textField;
    }

    public void actionPerformed(ActionEvent e) {
        if (text.equals("Search") || text.equals("Delete")) {
            JOptionPane.showMessageDialog(null, text + " Clicked!");
        }
        if(text.equals("Send")){
            if(!(textField.getText().isEmpty())){
                String mess = textField.getText();
                textField.setText(""); // Clear the text field after sending

                chatBox.getChatMessages().add(new ChatMessage(chatBox.getUsername(), "",mess));

                chatBox.displayChatMessage();
            }
        }
    }
}
