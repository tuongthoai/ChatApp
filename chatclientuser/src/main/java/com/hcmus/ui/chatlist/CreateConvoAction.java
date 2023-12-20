package com.hcmus.ui.chatlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class CreateConvoAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> listFriend = Arrays.asList("Tran Gia Thinh", "Lac Thieu Quan", "Tang Tuong Thoai", "Nguyen Cong Khanh", "Nguyen Cao Luan");
        CreateConvoDialog createConvoDialog = new CreateConvoDialog(listFriend);
    }
}
