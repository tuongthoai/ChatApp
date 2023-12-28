package com.hcmus.ui.friendscreen.listfriend;

import com.hcmus.models.Friend;
import com.hcmus.models.UserDTO;
import com.hcmus.services.FriendService;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.ReloadTable;
import com.hcmus.ui.table.Table;
import com.hcmus.utils.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class BlockDialog<T> extends JDialog {
    private final JPanel contentPane;
    private final Table<T> tablePanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private boolean isBlocked;
    private Class<?> clazz;
    public BlockDialog(Table<T> tablePanel, Class<?> clazz) {
        this.clazz = clazz;
        this.isBlocked = false;
        this.tablePanel = tablePanel;
        this.contentPane = createContentPanel();
        setContentPane(contentPane);
        setModal(true);
        setTitle("Confirm Unfriend");
        initializeButtons();

        this.buttonOK.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 12));
        this.buttonCancel.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 12));

        JLabel confirm;
        try {
            int userID = UserProfile.getUserProfile().getId();
            int friendID = -1;
            if (this.clazz == UserDTO.class) friendID = ((UserDTO) tablePanel.getSelectedData()).getId();
            else if (this.clazz == Friend.class) friendID = ((Friend) tablePanel.getSelectedData()).getId();
            if(isBlocked(userID, friendID)){
                isBlocked = true;
                confirm = new JLabel("Are you sure to unblock this person?");
            }else{
                confirm = new JLabel("Are you sure to block this person?");
            }
        } catch (Exception e) {
            e.printStackTrace();
            confirm = new JLabel("Can't check block status!!!");
        }
        confirm.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(this.buttonOK);
        buttonPane.add(this.buttonCancel);

        contentPane.add(confirm, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private boolean isBlocked(int userID, int friendID) throws Exception {
        return UserService.getInstance().isBlockedBy(userID, friendID);
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
        int friendID = -1;

        if(tablePanel.getSelectedData() == null){
            JOptionPane.showMessageDialog(this, "No selected user", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        if (this.clazz == UserDTO.class) friendID = ((UserDTO) tablePanel.getSelectedData()).getId();
        else if (this.clazz == Friend.class) friendID = ((Friend) tablePanel.getSelectedData()).getId();

        try {
            if (!isBlocked) UserService.getInstance().blockUser(userID, friendID);
            else UserService.getInstance().unblockUser(userID, friendID);
            JOptionPane.showMessageDialog(this, "Success", "Success", JOptionPane.INFORMATION_MESSAGE);
//            updateTable();
            if (isBlocked) ReloadTable.reloadBlockTable((Table<UserDTO>) tablePanel);
            else if (clazz == UserDTO.class) ReloadTable.reloadFriendTable((Table<UserDTO>) tablePanel, "All");
            else if (clazz == Friend.class) ReloadTable.reloadStrangerTable((Table<Friend>) tablePanel);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failure", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void onCancel(ActionEvent ev) {
        dispose();
    }
}
