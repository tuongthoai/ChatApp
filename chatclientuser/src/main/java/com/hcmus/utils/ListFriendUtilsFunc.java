package com.hcmus.utils;

import javax.swing.*;
import java.awt.*;

public class ListFriendUtilsFunc {
    private static ListFriendUtilsFunc instance = null;

    static {
        instance = new ListFriendUtilsFunc();
    }

    private CardLayout mainCard;
    private JPanel mainContentPanel;

    private ListFriendUtilsFunc() {

    }

    public static ListFriendUtilsFunc getInstance() {
        if (instance == null) {
            return instance = new ListFriendUtilsFunc();
        }

        return instance;
    }

    public CardLayout getMainCard() {
        return mainCard;
    }

    public void setMainCard(CardLayout mainCard) {
        this.mainCard = mainCard;
    }

    public JPanel getMainContentPanel() {
        return mainContentPanel;
    }

    public void setMainContentPanel(JPanel mainContentPanel) {
        this.mainContentPanel = mainContentPanel;
    }
    public void showContentPanel() {
        mainCard.show(mainContentPanel, "CHAT");
    }
}
