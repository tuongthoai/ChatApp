package com.hcmus.ui.chatlist;

import javax.swing.*;
import javax.swing.border.Border;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ClientChatMessage;
import com.hcmus.models.User;
import com.hcmus.services.GChatService;
import com.hcmus.services.UserService;
import com.hcmus.socket.ChatContext;
import com.hcmus.utils.UserProfile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateNewConversationDlg extends JDialog implements ActionListener{
    private ChatList parent;
    private List<User> friends;
    private JPanel metaDataDlg;
    private JScrollPane listFriendDlg;
    private JPanel btnPnl;
    private List<User> selectedUser = new ArrayList<>();
    private JTextField groupNameText;

    public CreateNewConversationDlg(ChatList parent, List<User> friends) {
        this.parent = parent;
        this.friends = friends;
        setTitle("CREATE NEW DIALOG");
        setSize(new Dimension(400, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        metaDataDlg = new JPanel();
        JLabel gNameLbl = new JLabel("CHAT NAME:");
        gNameLbl.setPreferredSize(new Dimension(100, 30));
        gNameLbl.setFont(new Font("Serif", Font.PLAIN, 15));
        metaDataDlg.add(gNameLbl);

        groupNameText = new JTextField();
        groupNameText.setPreferredSize(new Dimension(250, 30));
        metaDataDlg.add(groupNameText);

        add(metaDataDlg, BorderLayout.NORTH);

        btnPnl = new JPanel();
        JButton submitBtn = new JButton("Submit");
        submitBtn.setPreferredSize(new Dimension(100, 30));
        btnPnl.add(submitBtn);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(100, 30));
        btnPnl.add(cancelBtn);
        add(btnPnl, BorderLayout.SOUTH);

        // handle content panel
        selectedUser.clear();
        listFriendDlg = createFriendListPanel();
        add(listFriendDlg, BorderLayout.CENTER);

        // add action to button
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        submitBtn.addActionListener(this);
    }

    private JScrollPane createFriendListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (User friend : friends) {
            JCheckBox userCheckbox = new JCheckBox(friend.getUsername());
            userCheckbox.setFont(new Font("Serif", Font.PLAIN, 18)); // Set the font size
            userCheckbox.setPreferredSize(new Dimension(300, 40)); // Set preferred size
            userCheckbox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCheckbox(userCheckbox, friend);
                }
            });
            panel.add(userCheckbox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        return scrollPane;
    }

    private void handleCheckbox(JCheckBox checkbox, User user) {
        if (checkbox.isSelected()) {
            if (!selectedUser.contains(user)) {
                selectedUser.add(user);
                System.out.println("User added: " + user.getUsername());
            }
        } else {
            selectedUser.remove(user);
            System.out.println("User removed: " + user.getUsername());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        GChatService service = GChatService.getInstance();
        try {
            String chatName = groupNameText.getText().trim();
            if (chatName == null || chatName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Group name is not empty", "Error message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserService userService = UserService.getInstance();
            User curUser = userService.getUserById(UserProfile.getUserProfile().getId());
            selectedUser.add(curUser);
            int result = service.creatAGroupChat(chatName, UserProfile.getUserProfile().getId(), selectedUser); //return groupchat id
            if(result > 0) {
                ClientChatMessage sysUpdateMsg = new ClientChatMessage();
                sysUpdateMsg.setMsgType("SYS");
                sysUpdateMsg.setMsgContent("UPDATE->CHAT_SCREEN");
                sysUpdateMsg.setGroupChatId(result);
                ChatContext.getInstance().send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
                ChatContext chatContext = ChatContext.getInstance();
                chatContext.send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
                JOptionPane.showMessageDialog(this, "Create successfully", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                parent.reloadChatList();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Network Error", "Error message", JOptionPane.ERROR_MESSAGE);
        }
    }
}
