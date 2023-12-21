package com.hcmus.ui.chatbox;

import com.hcmus.services.GChatService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

public class RenameFieldBtnActionImpl implements ActionListener {
    private final ChatBox parent;
    private final RenameField input;

    public RenameFieldBtnActionImpl(ChatBox parent, RenameField renameField) {
        this.parent = parent;
        this.input = renameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newName = input.getText().trim();
        try {
            try {
                GChatService.getInstance().renameGroupChat(parent.getChatId(), newName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            parent.getChatScreen().reloadChatScreen();
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
