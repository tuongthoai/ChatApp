package com.hcmus.ui.table;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.spam.SpamReport;
import com.hcmus.entities.user.User;
import com.hcmus.services.GChatService;
import com.hcmus.services.SpamService;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.Table;

import java.util.List;

public class ReloadTable {
    public static void reloadUserTable(Table<User> tablePanel) {
        try {
            UserService service = new UserService();
            List<User> data = service.getAllUsers();
            tablePanel.updateData(data);
            tablePanel.updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void reloadGroupChatTable(Table<GroupChat> tablePanel) {
        try {
            GChatService service = new GChatService();
            List<GroupChat> data = service.getAllGChats();
            tablePanel.updateData(data);
            tablePanel.updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadSpamTable(Table<SpamReport> tablePanel) {
        try {
            SpamService service = new SpamService();
            List<SpamReport> data = service.getAllSpamReports();
            tablePanel.updateData(data);
            tablePanel.updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
