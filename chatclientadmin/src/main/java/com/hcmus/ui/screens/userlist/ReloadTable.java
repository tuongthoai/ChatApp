package com.hcmus.ui.screens.userlist;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.Table;

import java.util.List;

public class ReloadTable {
    public static void reload(Table<User> tablePanel) {
        try {
            UserListService service = new UserListService();
            List<User> data = service.getAllUsers();
            tablePanel.updateData(data);
            tablePanel.updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
