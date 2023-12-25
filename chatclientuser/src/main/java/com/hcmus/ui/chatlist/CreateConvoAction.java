package com.hcmus.ui.chatlist;

import com.hcmus.models.Friend;
import com.hcmus.models.User;
import com.hcmus.services.FriendService;
import com.hcmus.services.UserService;
import com.hcmus.utils.UserProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateConvoAction implements ActionListener {
    private JPanel parent;
    public CreateConvoAction(ChatList chatList) {
        this.parent = chatList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        List<String> listFriend = Arrays.asList("Tran Gia Thinh", "Lac Thieu Quan", "Tang Tuong Thoai", "Nguyen Cong Khanh", "Nguyen Cao Luan");
//        CreateConvoDialog createConvoDialog = new CreateConvoDialog(listFriend);

        UserService service = UserService.getInstance();
        List<User> friends = new ArrayList<>();
        try {
            friends = service.findAllFriends(UserProfile.getUserProfile().getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        CreateNewConversationDlg conversationDlg = new CreateNewConversationDlg(parent, friends);
        conversationDlg.setVisible(true);
    }
}
