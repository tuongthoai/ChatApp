package com.hcmus.ui.friendscreen.listfriend;

import com.hcmus.UserProfile;
import com.hcmus.models.User;
import com.hcmus.models.UserDTO;
import com.hcmus.services.FriendService;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.ReloadTable;
import com.hcmus.ui.table.Table;
import com.hcmus.ui.table.UnixTimestampConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UnfriendDialog extends JDialog {
    private final JPanel contentPane;
    private final Table<UserDTO> tablePanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    public UnfriendDialog(Table<UserDTO> tablePanel) {
        this.tablePanel = tablePanel;
        this.contentPane = createContentPanel();
        setContentPane(contentPane);
        setModal(true);
        setTitle("Confirm Unfriend");
        initializeButtons();

        this.buttonOK.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 12));
        this.buttonCancel.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 12));

        JLabel confirm = new JLabel("Are you certain that you want to unfriend?");
        confirm.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(this.buttonOK);
        buttonPane.add(this.buttonCancel);

        contentPane.add(confirm, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(345, 100));
        panel.setLayout(new BorderLayout());
        return panel;
    }
    private void initializeButtons() {
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(ev -> {
            try {
                onOK(ev);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonCancel.addActionListener(this::onCancel);
    }
    private void onOK(ActionEvent ev) throws IOException {
        int userID = UserProfile.getUserProfile().getId();
        int friendID;

        if(tablePanel.getSelectedData() == null){
            JOptionPane.showMessageDialog(this, "No selected friend", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        friendID = tablePanel.getSelectedData().getId();

        try {
            FriendService service = new FriendService();
            service.unfriend(userID, friendID);
            JOptionPane.showMessageDialog(this, "Unfriend successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//            updateTable();
            ReloadTable.reloadFriendTable(tablePanel);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unfriend failed", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
    }
    private void onCancel(ActionEvent ev) {
        dispose();
    }

    private void updateTable(){
        UserService service = UserService.getInstance();
        List<User> listfriend = new ArrayList<>();
        List<UserDTO> result = new ArrayList<>();
        try {
            listfriend = service.findAllFriends(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User friend : listfriend){
            UserDTO userDTO = new UserDTO();

            userDTO.setId(friend.getId());
            userDTO.setUsername(friend.getUsername());
            userDTO.setFullname(friend.getName());
            if(friend.isOnline()){
                userDTO.setOnline("Online");
            } else {
                userDTO.setOnline("Offline");
            }

            result.add(userDTO);
        }

        tablePanel.updateData(result);
        tablePanel.updateTable();
    }
}
