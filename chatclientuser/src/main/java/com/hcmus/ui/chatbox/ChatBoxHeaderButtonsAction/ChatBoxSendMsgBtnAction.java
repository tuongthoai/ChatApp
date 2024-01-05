package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.utils.UserProfile;
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
        if (parent.getChatId() == null) {
            JOptionPane.showMessageDialog(parent, "You are not in any group chat");
            return;
        }
        if (!(jTextField.getText().isEmpty())) {
            String content = jTextField.getText();
            jTextField.setText(""); // Clear the text field after sending

            ChatMessage msg = new ChatMessage();
            msg.setGroupChatId(parent.getChatId());
            msg.setUserSentId(UserProfile.getUserProfile().getId());
            msg.setUsername(UserProfile.getUserProfile().getUsername());
            msg.setMsgContent(content);

            try {
                parent.getContext().send(mapper.writeValueAsString(msg));

            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
