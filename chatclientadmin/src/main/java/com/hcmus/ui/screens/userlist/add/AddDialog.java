package com.hcmus.ui.screens.userlist.add;

import com.hcmus.entities.user.User;
import com.hcmus.ui.table.ReloadTable;
import com.hcmus.services.UserService;
import com.hcmus.ui.table.Table;
import com.hcmus.ui.table.UnixTimestampConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.List;

public class AddDialog extends JDialog {
    private final JPanel contentPane;
    private final Table<User> tablePanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField username;
    private JTextField password;
    private JTextField name;
    private JTextField email;
    private JComboBox<String> sexComboBox;
    private JTextField address;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;

    public AddDialog(Table<User> tablePanel) {
        this.tablePanel = tablePanel;
        this.contentPane = createContentPanel();
        setContentPane(contentPane);
        setModal(true);
        setTitle("Add new user");
        initializeButtons();
        initializeFields();
        setupLayout();
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(500, 300));
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    private void initializeButtons() {
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(this::onOK);
        buttonCancel.addActionListener(this::onCancel);
    }

    private void initializeFields() {
        username = new JTextField(50);
        password = new JTextField(50);
        name = new JTextField(50);
        email = new JTextField(50);
        sexComboBox = new JComboBox<>(new String[] {"Nam", "Nữ", "Khác"});
        dayComboBox = new JComboBox<>(createStringArray(1, 31));
        monthComboBox = new JComboBox<>(createStringArray(1, 12));
        yearComboBox = new JComboBox<>(createStringArray(1971, 2023));
        address = new JTextField(50);
    }

    private String[] createStringArray(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
    }

    private void setupLayout() {
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        JLabel nameLabel = new JLabel("Name");
        JLabel emailLabel = new JLabel("Email");
        JLabel sexLabel = new JLabel("Sex");
        JLabel birthdayLabel = new JLabel("Date of birth");
        JLabel dayLabel = new JLabel("Day");
        JLabel monthLabel = new JLabel("Month");
        JLabel yearLabel = new JLabel("Year");
        JLabel addressLabel = new JLabel("Address");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;

        contentPane.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        contentPane.add(username, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        contentPane.add(password, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        contentPane.add(name, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        contentPane.add(email, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(sexLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPane.add(sexComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 2;
        contentPane.add(birthdayLabel, gbc);
        gbc.gridheight = 1;

        gbc.gridx = 1;
        gbc.gridy = 5;
        contentPane.add(dayLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        contentPane.add(monthLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        contentPane.add(yearLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        contentPane.add(dayComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        contentPane.add(monthComboBox, gbc);

        gbc.gridx = 3;
        gbc.gridy = 6;
        contentPane.add(yearComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPane.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        contentPane.add(address, gbc);
        gbc.gridwidth = 1;

        gbc.insets = new Insets(10, 5, 2, 5);
        gbc.gridx = 1;
        gbc.gridy = 8;
        contentPane.add(buttonOK, gbc);

        gbc.gridx = 2;
        gbc.gridy = 8;
        contentPane.add(buttonCancel, gbc);
    }

    private boolean isValidDate(int day, int month, int year) {
        List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1971 || year > 2023) {
            return false;
        }
        if (month == 2 && year % 4 == 0 && year % 100 != 0) {
            return day <= 29;
        }
        return day <= daysInMonth.get(month - 1);
    }

    private void onOK(ActionEvent ev) {
        String username = this.username.getText();
        String password = this.password.getText();
        String name = this.name.getText();
        String email = this.email.getText();
        String address = this.address.getText();
        String sex = (String) this.sexComboBox.getSelectedItem();
        String day = (String) dayComboBox.getSelectedItem();
        String month = (String) monthComboBox.getSelectedItem();
        String year = (String) yearComboBox.getSelectedItem();


        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (day.isEmpty() || month.isEmpty() || year.isEmpty() || !isValidDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year))) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // add new user to database
        LocalDateTime localDate = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0, 0);
        long unixBirthday = UnixTimestampConverter.dateTime2Unix(localDate);
        User newUser = new User(1, username, password, name, email, sex, address, unixBirthday, System.currentTimeMillis(), false, false);
        try {
            UserService service = new UserService();
            service.addUser(newUser);
            JOptionPane.showMessageDialog(this, "Add user successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            ReloadTable.reloadUserTable(tablePanel);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Add user failed", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);
            return;
        }
    }

    private void onCancel(ActionEvent ev) {
        dispose();
    }
}
