package com.hcmus.ui.chatbox.MemberListAction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.utils.UserProfile;
import com.hcmus.models.ClientChatMessage;
import com.hcmus.models.User;
import com.hcmus.services.GChatService;
import com.hcmus.services.UserService;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatbox.MemberList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class AddMemberListAction implements ActionListener {
    private final MemberList parent;
    private int selectedIndice = -1;

    public AddMemberListAction(MemberList parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // them gi do vao
        JDialog selectMemberAddDlg = new JDialog();
        selectMemberAddDlg.setModal(true);
        selectMemberAddDlg.setLayout(new FlowLayout());
        selectMemberAddDlg.setSize(new Dimension(600, 400));
        selectMemberAddDlg.setTitle("Select a member to add to group");
        selectMemberAddDlg.setLocationRelativeTo(null);

        UserService service = UserService.getInstance();
        List<User> friends = null;
        try {
            friends = service.getAllFriendNotInGroup(UserProfile.getUserProfile().getId(), parent.getChatId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        List<String> usernames = friends.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        JList<String> usernameList = new JList<>(usernames.toArray(new String[0]));
        usernameList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedIndice = usernameList.getSelectedIndex();
            }
        });
        JScrollPane userSelectionPnl = new JScrollPane(usernameList);
        userSelectionPnl.setPreferredSize(new Dimension(580, 300));
        userSelectionPnl.setFont(new Font("Aria", Font.PLAIN, 14));

        selectMemberAddDlg.add(userSelectionPnl, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setSize(new Dimension(580, 30));

        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(addBtn);
        btnPanel.add(cancelBtn);

        selectMemberAddDlg.add(btnPanel, BorderLayout.SOUTH);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMemberAddDlg.dispose();
            }
        });

        List<User> finalFriends = friends;
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndice == -1) {
                    JOptionPane.showMessageDialog(
                            selectMemberAddDlg,
                            "You need to choose a friend",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                if (finalFriends == null || finalFriends.isEmpty()) {
                    return;
                }
                User selectedUser = finalFriends.get(selectedIndice);

                // Add this member to group
                GChatService gChatService = GChatService.getInstance();
                try {
                    gChatService.addMember2Group(parent.getChatId(), selectedUser.getId());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            selectMemberAddDlg,
                            "Network error",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                // send reload msg to

                ClientChatMessage sysUpdateMsg = new ClientChatMessage();
                sysUpdateMsg.setMsgType("SYS");
                sysUpdateMsg.setMsgContent("UPDATE->MEMBER_LIST");
                sysUpdateMsg.setGroupChatId(parent.getChatId());

                try {
                    ChatContext.getInstance().send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }

                selectMemberAddDlg.dispose();
            }
        });

        selectMemberAddDlg.setVisible(true);
    }
}
