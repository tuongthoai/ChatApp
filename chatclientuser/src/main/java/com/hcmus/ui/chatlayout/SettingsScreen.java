package com.hcmus.ui.chatlayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmus.utils.Link;
import com.hcmus.utils.UserProfile;
import com.hcmus.models.User;
import com.hcmus.services.AuthService;
import com.hcmus.services.GChatService;
import com.hcmus.services.UserService;
import com.hcmus.ui.loginscreens.Login;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class SettingsScreen extends JPanel {
    private JButton button;
    private JButton button1;
    private ChangePasswordScreen changePasswordScreen;
    private User user = null;
    private UserService userService = UserService.getInstance();
    private JLabel nameLabel;
    private JLabel sexLabel;
    private JLabel birthdayLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel friendsNumber;
    private JLabel groupsNumber;
    private JLabel lastLogin;
    private JPanel centerPanel;

    public SettingsScreen() {
        setLayout(new BorderLayout());

        JPanel northPanel = initNorthPanel();
        this.centerPanel = initCenterPanel(null);
        JPanel southPanel = initSouthPanel();

        add(northPanel, BorderLayout.NORTH);
        add(this.centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user = userService.getUserById(UserProfile.getUserProfile().getId());
                } catch (Exception err) {
                    err.printStackTrace(System.err);
                }
                if (user == null) {
                    user = new User();
                    user.setName("DEFAULT_NAME");
                }

                nameLabel.setText("Name: " + getUser().getName());
                sexLabel.setText("Sex: " + getUser().getSex());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                birthdayLabel.setText("Birthday: " + formatter.format(new Date(getUser().getBirthday())));
                addressLabel.setText("Address: " + getUser().getAddress());
                emailLabel.setText("Email: " + getUser().getEmail());
                long friendCnt = 0;
                try {
                    friendCnt = userService.countFriends(getUser().getId());
                } catch (Exception err) {
                    err.printStackTrace(System.err);
                }
                friendsNumber.setText("Friends: " + friendCnt);
                GChatService gChatService = GChatService.getInstance();
                long gChatCnt = 0;
                try {
                    gChatCnt = gChatService.countNoGroupChat(getUser().getId());
                } catch (Exception err) {
                    err.printStackTrace(System.err);
                }
                groupsNumber.setText("Groups: " + gChatCnt);
                AuthService authService = AuthService.getInstance();
                long lastLoginTimeStamp = 0;
                try {
                    lastLoginTimeStamp = authService.lastLogin(getUser().getId());
                } catch (Exception err) {
                    err.printStackTrace(System.err);
                }
                formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                lastLogin.setText("Last login: " + formatter.format(new Date(lastLoginTimeStamp)));

            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(refresh);
        // add popup menu to right click
        this.setComponentPopupMenu(popupMenu);

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
        avatar.setIcon(new ImageIcon(Link.getLink("image") + "avatar.png"));
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

        JButton editButton = new JButton("Edit Profile");
        editButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditDialog();
            }
        });

        northPanel.add(editButton, BorderLayout.EAST);

        return northPanel;
    }

    private JPanel initCenterPanel(JPanel panel) {
        JPanel centerPanel = new JPanel();
        if (panel != null) {
            centerPanel = panel;
        }
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Information");
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);

        JPanel infoPanel = new JPanel();
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 180, 10, 180));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel firstRow = new JPanel();
        firstRow.setLayout(new BorderLayout(20, 0));

        nameLabel = new JLabel("Name: " + getUser().getName());
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        firstRow.add(nameLabel, BorderLayout.WEST);

        sexLabel = new JLabel("Sex: " + getUser().getSex());
        sexLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        sexLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        firstRow.add(sexLabel, BorderLayout.EAST);
        infoPanel.add(firstRow);

        JPanel secondRow = new JPanel();
        secondRow.setLayout(new BorderLayout(20, 0));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        birthdayLabel = new JLabel("Birthday: " + formatter.format(new Date(getUser().getBirthday())));
        birthdayLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        birthdayLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        secondRow.add(birthdayLabel, BorderLayout.WEST);

        addressLabel = new JLabel("Address: " + getUser().getAddress());
        addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        addressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        secondRow.add(addressLabel, BorderLayout.EAST);
        infoPanel.add(secondRow);

        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BorderLayout(20, 0));
        emailLabel = new JLabel("Email: " + getUser().getEmail());
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        thirdRow.add(emailLabel, BorderLayout.WEST);
        infoPanel.add(thirdRow);

        UserService service = UserService.getInstance();
        long friendCnt = 0;
        try {
            friendCnt = service.countFriends(getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel fourthRow = new JPanel();
        fourthRow.setLayout(new BorderLayout(20, 0));


        friendsNumber = new JLabel("Friends: " + friendCnt);
        friendsNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        friendsNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        fourthRow.add(friendsNumber, BorderLayout.WEST);


        GChatService gChatService = GChatService.getInstance();
        long gChatCnt = 0;
        try {
            gChatCnt = gChatService.countNoGroupChat(getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        groupsNumber = new JLabel("Groups: " + gChatCnt);
        groupsNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        groupsNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        fourthRow.add(groupsNumber, BorderLayout.EAST);
        infoPanel.add(fourthRow);
        AuthService authService = AuthService.getInstance();
        long lastLoginTimeStamp = 0;
        try {
            lastLoginTimeStamp = authService.lastLogin(getUser().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JPanel fifthRow = new JPanel();
        fifthRow.setLayout(new BorderLayout(20, 0));

        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        lastLogin = new JLabel("Last login: " + formatter.format(new Date(lastLoginTimeStamp)));
        lastLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lastLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        fifthRow.add(lastLogin, BorderLayout.WEST);
        infoPanel.add(fifthRow);

        centerPanel.add(infoPanel);
        return centerPanel;
    }

    private void reloadCenterPanel() {
        this.centerPanel.removeAll();
        initCenterPanel(this.centerPanel);
        revalidate();
        repaint();
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

    private void logout() throws JsonProcessingException {
        AuthService authService = AuthService.getInstance();
        // dispose all frame
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
        // open login frame
        Login login = new Login();
        login.setVisible(true);
        // set disconnect time
        authService.setDCTime(UserProfile.getUserProfile().getId());

        UserProfile.removeUserProfile();
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
                try {
                    logout();
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
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

    private void showEditDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Profile");
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel("Edit Profile");
        JLabel nameLabel = new JLabel("Name");
        JLabel sexLabel = new JLabel("Sex");
        JLabel birthdayLabel = new JLabel("Birthday");
        JLabel addressLabel = new JLabel("Address");

        JTextField nameField = new JTextField(20);
        nameField.setText(getUser().getName());
        JComboBox<String> sexBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        sexBox.setSelectedItem(getUser().getSex());
        JDatePickerImpl birthdayPicker = initDatePickers();
        // set date
        DateModel<?> dateModel = birthdayPicker.getModel();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getUser().getBirthday());
        dateModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        JTextField addressField = new JTextField(20);
        addressField.setText(getUser().getAddress() == null ? "" : getUser().getAddress());

        JButton saveButton = new JButton("Save");

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(20));
        namePanel.add(nameField);

        JPanel sexPanel = new JPanel();
        sexPanel.setLayout(new BoxLayout(sexPanel, BoxLayout.X_AXIS));
        sexPanel.add(sexLabel);
        sexPanel.add(Box.createHorizontalStrut(20));
        sexPanel.add(sexBox);

        JPanel birthdayPanel = new JPanel();
        birthdayPanel.setLayout(new BoxLayout(birthdayPanel, BoxLayout.X_AXIS));
        birthdayPanel.add(birthdayLabel);
        birthdayPanel.add(Box.createHorizontalStrut(20));
        birthdayPanel.add(birthdayPicker);

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.add(addressLabel);
        addressPanel.add(Box.createHorizontalStrut(20));
        addressPanel.add(addressField);

        panel.add(label);
        panel.add(namePanel);
        panel.add(sexPanel);
        panel.add(birthdayPanel);
        panel.add(addressPanel);
        panel.add(saveButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);

        saveButton.addActionListener(e -> {
            User user = UserProfile.getUserProfile();
            if (!nameField.getText().isEmpty()) {
                user.setName(nameField.getText());
            }
            if (sexBox.getSelectedIndex() != -1) {
                user.setSex((String) sexBox.getSelectedItem());
            }
            if (birthdayPicker.getModel().getValue() != null) {
                // convert to epoch time
                Date date = (Date) birthdayPicker.getModel().getValue();
                Instant instant = date.toInstant();

                user.setBirthday(instant.toEpochMilli());
            }
            if (!addressField.getText().isEmpty()) {
                user.setAddress(addressField.getText());
            }

            try {
                userService.updateUser(user);
                User usr = null;
                usr = UserService.getInstance().getUserById(UserProfile.getUserProfile().getId());
                UserProfile.setUserProfile(usr);
                this.user = usr;
                JOptionPane.showMessageDialog(null, "Your profile has been updated!");
                dialog.dispose();
                reloadCenterPanel();
            } catch (Exception err) {
                err.printStackTrace(System.err);
            }
        });
    }

    private JDatePickerImpl initDatePickers() {
        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String pattern = "yyyy/MM/dd";
        private final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            try {
                return dateFormat.parseObject(text);
            } catch (ParseException e) {
                // Handle the exception or return null if parsing fails
                e.printStackTrace(System.err);
                return null;
            }
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value instanceof Date) {
                return dateFormat.format((Date) value);
            } else if (value instanceof Calendar) {
                return dateFormat.format(((Calendar) value).getTime());
            }
            return "";
        }
    }
}
