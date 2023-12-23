package com.hcmus.ui.table;

import com.hcmus.entities.groupchat.GroupChat;
import com.hcmus.entities.spam.SpamReport;
import com.hcmus.entities.user.User;
import com.hcmus.entities.user.UserActivity;
import com.hcmus.services.*;
import com.hcmus.ui.table.Table;

import java.util.List;

public class ReloadTable<T> {

    public void reloadTable(Table<T> tablePanel, Object service, Class<T> clazz) {
        try {
            List<T> data = null;
            switch (service.getClass().getSimpleName()) {
                case "UserService":
                    if (clazz == User.class) {
                        data = (List<T>) ((UserService) service).getAllUsers();
                    } else if (clazz == UserActivity.class) {
                        data = (List<T>) ((UserService) service).getAllUserActivity();
                    }
                    break;
                case "GChatService":
                    data = (List<T>) ((GChatService) service).getAllGChats();
                    break;
                case "UserFriendService":
                    data = (List<T>) ((UserFriendService) service).getDirInDirFriend();
                    break;
                case "SpamService":
                    data = (List<T>) ((SpamService) service).getAllSpamReports();
                    break;
                case "LoginHistoryService":
                    data = (List<T>) ((LoginHistoryService) service).getUserLoginTime();
                    break;

            }
            tablePanel.updateData(data);
            tablePanel.updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
