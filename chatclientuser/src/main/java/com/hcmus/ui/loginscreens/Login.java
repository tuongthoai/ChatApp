package com.hcmus.ui.loginscreens;

import com.hcmus.services.EventHandlerService;
import com.hcmus.utils.UserProfile;
import com.hcmus.models.User;
import com.hcmus.services.AuthService;
import com.hcmus.services.UserService;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatlayout.ChatLayout;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {
    private JPanel panel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblRegister;
    private JLabel lblForgotPassword;
    private ChatLayout chatLayout;

    public Login() {
        init();
        addListener();
    }

    private void init() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setBounds(100, 100, 800, 600);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblLogin = new JLabel("Log in to get started");
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        panel.add(lblLogin, gbc);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(lblUsername, gbc);

        txtUsername = new JTextField(22);
        txtUsername.setMargin(new Insets(10, 5, 10, 5));
        txtUsername.setBackground(Color.WHITE);
        txtUsername.setForeground(Color.GRAY);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        panel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(22);
        txtPassword.setMargin(new Insets(10, 5, 10, 5));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setForeground(Color.GRAY);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(txtPassword, gbc);

        lblRegister = new JLabel("Register");
        lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblRegister, gbc);

        lblForgotPassword = new JLabel("Forgot password?");
        lblForgotPassword.setForeground(new Color(128, 128, 128));
        lblForgotPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblForgotPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblForgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblForgotPassword, gbc);

        btnLogin = new JButton("Log in");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);
    }

    private void addListener() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter username and password");
                    return;
                }

                AuthService authService = AuthService.getInstance();
                int userId = -1;
                try {
                    userId = authService.login(username, password);
                    if (userId == -1) {
                        JOptionPane.showMessageDialog(null, "Invalid username or password!");
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Login failed!");
                    return;
                }

//                JOptionPane.showMessageDialog(null, "Login success!");

                // set current user
                try {
                    UserService service = UserService.getInstance();
                    User user = service.getUserById(userId);
                    UserProfile.setUserProfile(user);

                    // init websocket
                    Map<String, String> wsHeaders = new HashMap<>();
                    URI wsUri = new URI("ws://localhost:8080/chat");
                    wsHeaders.put("USER_SEND_ID", String.valueOf(UserProfile.getUserProfile().getId()));
                    ChatContext.getInstance(wsUri, wsHeaders);

                    // set login time
                    authService.setLoginTime(userId);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to get user info!");
                    return;
                }

                // if login success, do:
                try {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            chatLayout = new ChatLayout();
                            EventHandlerService.getInstance().addObserver(chatLayout);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        chatLayout.setVisible(true);
                    });
                    // close current frame
                    dispose();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose(); // Close current frame
                new Register().setVisible(true);
            }
        });

        lblForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose(); // Close current frame
                new ForgotPassword().setVisible(true);
            }
        });
    }
}
