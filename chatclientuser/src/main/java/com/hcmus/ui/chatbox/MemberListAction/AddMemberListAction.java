package com.hcmus.ui.chatbox.MemberListAction;

import com.hcmus.UserProfile;
import com.hcmus.models.User;
import com.hcmus.services.UserService;
import com.hcmus.ui.chatbox.MemberList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class AddMemberListAction implements ActionListener {
    private final MemberList parent;

    public AddMemberListAction(MemberList parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // them gi do vao
        JDialog selectMemberAddDlg = new JDialog();
        selectMemberAddDlg.setLayout(new BorderLayout());
        selectMemberAddDlg.setSize(new Dimension(600, 400));
        selectMemberAddDlg.setTitle("Select a member to add to group");
        selectMemberAddDlg.setLocationRelativeTo(null);

        UserService service = UserService.getInstance();
        List<User> friends = null;
        try {
            friends = service.findAllFriends(UserProfile.getUserProfile().getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(selectMemberAddDlg, "Network Error", "Error message", JOptionPane.ERROR_MESSAGE);
        }
        List<String> usernames = friends.stream().map(User::getUsername).collect(Collectors.toList());
        JList<String> usernameList = new JList<>(usernames.toArray(new String[0]));
        JScrollPane userSelectionPnl = new JScrollPane(usernameList);
        userSelectionPnl.setPreferredSize(new Dimension(580, 250));
        userSelectionPnl.setFont(new Font("Aria", Font.PLAIN, 14));

        selectMemberAddDlg.add(userSelectionPnl, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setSize(new Dimension(580, 100));

        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(addBtn);
        btnPanel.add(cancelBtn);

        selectMemberAddDlg.add(btnPanel, BorderLayout.SOUTH);

        selectMemberAddDlg.setVisible(true);
//        parent.updateMemberList();
    }
}
