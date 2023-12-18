package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.hcmus.models.ChatMessage;
import com.hcmus.ui.chatbox.ChatBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatBoxSearchAction implements ActionListener {
    private ChatBox chatBox;

    private String searchKey;
    public ChatBoxSearchAction(ChatBox chatBox) {
        this.chatBox = chatBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog searchInfoDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(chatBox));
        searchInfoDialog.setTitle("Message Search");
        searchInfoDialog.setLocationRelativeTo(null);
        searchInfoDialog.setSize(400, 150);
        searchInfoDialog.setLayout(new FlowLayout());

        JTextField msgInfo = new JTextField(25);
        msgInfo.setForeground(Color.BLACK);
        msgInfo.setBackground(Color.WHITE);
        msgInfo.setPreferredSize(new Dimension(100,30));
        JButton searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(100, 30));
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchKey = msgInfo.getText().trim();
                System.out.println(searchKey);
                searchInfoDialog.dispose();

                JDialog searchRsDlg = new JDialog();
                searchRsDlg.setLayout(new FlowLayout());
                searchRsDlg.setTitle("Result");
                searchRsDlg.setSize(600, 350);
                searchRsDlg.setLocationRelativeTo(null);
                DefaultListModel<String> listModel = new DefaultListModel<>();
                JList<String> messageJList = new JList<>(listModel);

                // iterate through all message to search
                List<ChatMessage> messages = chatBox.getChatMessages();
                for(ChatMessage msg : messages) {
                    listModel.addElement(formatChatMessage(msg));
                }

                JScrollPane searchRsScrollPanel = new JScrollPane(messageJList);
                searchRsScrollPanel.setPreferredSize(new Dimension(400, 300));
                searchRsDlg.add(searchRsScrollPanel);

                JButton confirmBtn = new JButton("DONE");
                confirmBtn.setPreferredSize(new Dimension(100, 50));
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        searchRsDlg.dispose();
                    }
                });

                searchRsDlg.add(confirmBtn);
                searchRsDlg.setVisible(true);
            }
        });

        searchInfoDialog.add(msgInfo);
        searchInfoDialog.add(searchBtn);
        searchInfoDialog.setVisible(true);
    }

    // Helper method to format a ChatMessage for display
    private String formatChatMessage(ChatMessage chatMessage) {
        Map<String, String> headers = chatMessage.getHeaders();
        String content = chatMessage.getContent();
        return "Headers: " + headers + "\nContent: " + content;
    }
}
