package com.hcmus.ui.table;

import com.hcmus.entities.user.User;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.Table;

import java.util.List;

public class ReloadTable {
    public static void reload(Table<User> tablePanel) {
        try {
            UserService service = new UserService();
            List<User> data = service.getAllUsers();
            tablePanel.updateData(data);
            tablePanel.updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
