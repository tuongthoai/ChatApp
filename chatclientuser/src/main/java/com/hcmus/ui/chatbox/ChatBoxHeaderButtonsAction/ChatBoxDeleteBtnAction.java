package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ClientChatMessage;
import com.hcmus.services.GChatService;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.utils.UserProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBoxDeleteBtnAction implements ActionListener {
    private ChatBox parent;
    public ChatBoxDeleteBtnAction(ChatBox chatBox) {
        this.parent = chatBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (parent.getChatId() == null) {
            JOptionPane.showMessageDialog(parent, "You are not in any group chat");
            return;
        }
        // checking if this user is admin of the group
        GChatService service = GChatService.getInstance();
        try {
            if (!service.isGroupAdmin(parent.getChatId(), UserProfile.getUserProfile().getId())) {
                JOptionPane.showMessageDialog(parent, "You are not group admin");
                return;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
            service.removeGroupChat(parent.getChatId());

            // send msg for reload UI
            ClientChatMessage sysUpdateMsg = new ClientChatMessage();
            sysUpdateMsg.setMsgType("SYS");
            sysUpdateMsg.setMsgContent("UPDATE->CHAT_SCREEN");
            sysUpdateMsg.setGroupChatId(parent.getChatId());
            ChatContext.getInstance().send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
            ChatContext chatContext = ChatContext.getInstance();
            chatContext.send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Networking Error. Can't remove chat");
        }

        JOptionPane.showMessageDialog(parent, "Group remove successfully");
    }
}
