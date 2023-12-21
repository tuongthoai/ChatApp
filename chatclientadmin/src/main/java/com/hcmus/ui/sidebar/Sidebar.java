package com.hcmus.ui.sidebar;

import com.hcmus.Link;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class Sidebar extends JPanel {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton selectedButton, button1, button2, button3, button4;
    private JPopupMenu currentPopupMenu;

    public Sidebar() {}

    public Sidebar(JPanel contentPanel, CardLayout cardLayout) {
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;

        setBackground(new Color(204, 255, 204));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        selectedButton = null;

        // access current directory by get resource
        button1 = createButton("Users", "user.png");
        button2 = createButton("Chats", "chat.png");
        button3 = createButton("Reports", "report.png");
        button4 = createButton("Statistics", "chart.png");

        add(button1);
        add(button2);
        add(button3);
        add(button4);

        selectedButton = button1;
    }

    private JButton createButton(String text, String iconFilename) {
        JButton button = new JButton();

        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
//        button.setText(text);

        // Load the icon
        ImageIcon icon = new ImageIcon(Link.getLink("image") + iconFilename);
        button.setIcon(icon);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(204, 255, 204));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Add vertical spacing around the button
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(153, 255, 153));
            }

            public void mouseExited(MouseEvent evt) {
                if (button != selectedButton) {
                    button.setBackground(new Color(204, 255, 204));
                }
            }

            public void mousePressed(MouseEvent evt) {
                List<String> options;
                switch (text) {
                    case "Users":
                        options = Arrays.asList(
                                "User list",
                                "Login history",
                                "New users",
                                "Users' friends"
                        );
                        break;
                    case "Chats":
                        options = Arrays.asList(
                                "Chat list",
                                "Online users"
                        );
                        break;
                    case "Reports":
                        options = Arrays.asList(
                                "Spam list"
                        );
                        break;
                    case "Statistics":
                        options = Arrays.asList(
                                "Registration statistics",
                                "Active user statistics"
                        );
                        break;
                    default:
                        options = Arrays.asList(
                                "Edit",
                                "Delete",
                                "Block",
                                "Detail",
                                "Login History",
                                "Friend List",
                                "Member List",
                                "Admin List"
                        );
                        break;
                }
                showMenu(button, evt.getX() + 10, evt.getY() + 10, options);
            }
        });

        // Add click effect
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedButton != null) {
                    selectedButton.setBackground(new Color(204, 255, 204));
                }
                button.setBackground(new Color(153, 255, 153));
                selectedButton = button;
            }
        });


        // Change the cursor to hand cursor
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    // show popup menu
    private void showMenu(Component component, int x, int y, List<String> options) {
        hideMenu();

        JPopupMenu popupMenu = new JPopupMenu();
        for (String option : options) {
//            System.out.println(option);
            JMenuItem menuItem = new JMenuItem(option);
            menuItem.addActionListener(new SideBarAction(option, cardLayout, contentPanel));
            popupMenu.add(menuItem);
        }

        popupMenu.show(component, x, y);
        currentPopupMenu = popupMenu;
    }

    private void hideMenu() {
        if (currentPopupMenu != null) {
            currentPopupMenu.setVisible(false);
            currentPopupMenu.removeAll();
            currentPopupMenu = null;
        }
    }
}