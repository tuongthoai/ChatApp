package com.hcmus.ui.chatlayout;

import com.hcmus.UserProfile;
import com.hcmus.models.GroupChat;
import com.hcmus.models.User;
import com.hcmus.services.AuthService;
import com.hcmus.services.GChatService;
import com.hcmus.services.UserService;
import com.hcmus.ui.loginscreens.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SettingsScreen extends JPanel {
    private static final String currentDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    private JButton button;
    private JButton button1;
    private ChangePasswordScreen changePasswordScreen;
    private User user = null;

    public SettingsScreen() {
        setLayout(new BorderLayout());

        JPanel northPanel = initNorthPanel();
        JPanel centerPanel = initCenterPanel();
        JPanel southPanel = initSouthPanel();

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        addListener();
    }

    private JPanel initNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        northPanel.setLayout(new BorderLayout(10, 10));
        northPanel.setBackground(new Color(204, 255, 204));

        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BorderLayout(10, 10));
        accountPanel.setBackground(new Color(0, 0, 0, 0));

        JLabel avatar = new JLabel();
        avatar.setIcon(new ImageIcon(currentDir + "avatar.png"));
        accountPanel.add(avatar, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1, 5, 5));
        infoPanel.setBackground(new Color(0, 0, 0, 0));

        JLabel username = new JLabel(getUser().getUsername());
        username.setFont(new Font("Tahoma", Font.BOLD, 20));
        infoPanel.add(username);

        JLabel status = new JLabel("Online");
        status.setFont(new Font("Tahoma", Font.PLAIN, 15));
        status.setForeground(Color.BLUE);
        infoPanel.add(status);

        accountPanel.add(infoPanel, BorderLayout.EAST);
        northPanel.add(accountPanel, BorderLayout.WEST);
        return northPanel;
    }

    private JPanel initCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Information");
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);

        JPanel infoPanel = new JPanel();
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 180, 10, 180));
        infoPanel.setLayout(new GridLayout(5, 1, 0, 5));

        JLabel email = new JLabel("Email: " + getUser().getEmail());
        email.setFont(new Font("Tahoma", Font.PLAIN, 15));
        email.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(email);

        UserService service = UserService.getInstance();
        long friendCnt = 0;
        try {
            friendCnt = service.countFriends(getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel friendsNumber = new JLabel("Friends: " + friendCnt);
        friendsNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        friendsNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(friendsNumber);

        GChatService gChatService = GChatService.getInstance();
        long gChatCnt = 0;
        try {
            gChatCnt = gChatService.countNoGroupChat(getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel groupsNumber = new JLabel("Groups: " + gChatCnt);
        groupsNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        groupsNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(groupsNumber);

//        JLabel messagesNumber = new JLabel("Messages: " + "100");
//        messagesNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
//        messagesNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
//        infoPanel.add(messagesNumber);

        AuthService authService = AuthService.getInstance();
        long lastLoginTimeStamp = 0;
        try {
            lastLoginTimeStamp = authService.lastLogin(getUser().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JLabel lastLogin = new JLabel("Last login: " + LocalDateTime.ofInstant(Instant.ofEpochMilli(lastLoginTimeStamp), ZoneId.systemDefault()));
        lastLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lastLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(lastLogin);

        centerPanel.add(infoPanel);
        return centerPanel;
    }

    private JPanel initSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2, 5, 5));

        button = new JButton("Change password");
        button1 = new JButton("Logout");
        button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        button1.setFont(new Font("Tahoma", Font.PLAIN, 20));

        southPanel.add(button);
        southPanel.add(button1);

        return southPanel;
    }

    private void changePassword() {
        changePasswordScreen = new ChangePasswordScreen(this);
        this.setVisible(false);
        this.getParent().add(changePasswordScreen, "changePassword");
        CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
        cardLayout.show(this.getParent(), "changePassword");
    }

    private void logout() {
        // dispose all frame
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
        // open login frame
        Login login = new Login();
        login.setVisible(true);
    }

    private void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    public User getUser() {
        if (this.user == null) {
            UserService service = UserService.getInstance();
            User curUser = null;
            try {
                curUser = service.getUserById(UserProfile.getUserProfile().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (curUser == null) {
                curUser = new User();
                curUser.setName("DEFAULT_NAME");
            }
            this.user = curUser;
        }
        return this.user;
    }
}
