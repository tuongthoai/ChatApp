package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ChatMessage;
import com.hcmus.ui.chatbox.ChatBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBoxSendMsgBtnAction implements ActionListener {
    private ChatBox parent;
    private JTextField jTextField;
    private ObjectMapper mapper;

    public ChatBoxSendMsgBtnAction(ChatBox parent, JTextField jTextField) {
        this.parent = parent;
        this.jTextField = jTextField;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(jTextField.getText().isEmpty())) {
            String mess = jTextField.getText();
            jTextField.setText(""); // Clear the text field after sending
            parent.displayChatMessage();

            ChatMessage msg = new ChatMessage();
            msg.setHeaders(parent.getMsgHeaders());
            msg.setContent(mess);
            try {
                parent.getContext().send(mapper.writeValueAsString(msg));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
