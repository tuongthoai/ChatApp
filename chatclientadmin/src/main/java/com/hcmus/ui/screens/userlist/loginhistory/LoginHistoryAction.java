package com.hcmus.ui.screens.userlist.loginhistory;

import com.hcmus.entities.loginhistory.LoginHistory;
import com.hcmus.entities.user.User;
import com.hcmus.services.LoginHistoryService;
import com.hcmus.ui.table.DetailList;
import com.hcmus.ui.table.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginHistoryAction implements ActionListener {
    private Table<User> tablePanel;
    private LoginHistoryService loginHistoryService;
    public LoginHistoryAction(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        User user = tablePanel.getSelectedData();
        loginHistoryService = new LoginHistoryService();

        List<LoginHistory> loginHistories = new ArrayList<>();

        try {
            loginHistories = loginHistoryService.getUserLoginHistory(user.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DetailList<LoginHistory> loginHistoryDialog = null;
        try {
            loginHistoryDialog = new DetailList<LoginHistory>(tablePanel, new Table<LoginHistory>(loginHistories, LoginHistory.getColumnNames()), "Login Histories - " + user.getUsername());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        loginHistoryDialog.setVisible(true);
    }
}
