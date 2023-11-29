package com.hcmus.ui.friendscreen;

import com.hcmus.ui.datatest.DataTest;
import com.hcmus.ui.datatest.User;
import com.hcmus.ui.datatest.UserFriend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListFriend extends JPanel {
    private DataTest data;
    private static final String currentDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    private String user_id;
    private CardLayout mainCard;
    private JPanel mainContentPanel;
    public ListFriend(DataTest data, CardLayout mainCard, JPanel mainContentPanel){
        this.mainCard = mainCard;
        this.data = data;
        this.user_id = "1";
        this.mainContentPanel = mainContentPanel;
        ArrayList<User> listFriend = getListFriend(data.getFriendList(), data.getUserList());

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("List Friends");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridBagLayout());
//        listPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font fontText = new Font("Serif", Font.PLAIN, 18);

        for(int i = 1; i < 5; i++){
            for(User friend : listFriend){
                JLabel nameLabel = new JLabel(friend.getFullname());
                nameLabel.setFont(fontText);
                gbc.gridx = 0;
                gbc.gridy++;
                gbc.weightx = 100;
                listPanel.add(nameLabel, gbc);

                JButton chatButton = createButton("chat", "chat-icon.png",friend.getUserId());
                gbc.gridx++;
                gbc.weightx = 1;
                listPanel.add(chatButton, gbc);

                JButton deleteButton = createButton("delete", "trash-solid.png",friend.getUserId());
                gbc.gridx++;
                listPanel.add(deleteButton, gbc);
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private ArrayList<User> getListFriend(ArrayList<UserFriend> userFriends, ArrayList<User> users){
        ArrayList<User> res = new ArrayList<>();
        for(UserFriend userFriend : userFriends){
            if(userFriend.getUserId().equals(this.user_id)){
                for(User user : users){
                    if(user.getUserId().equals(userFriend.getFriendId())) res.add(user);
                }
            }
        }
        return res;
    }
    private JButton createButton(String type, String iconPath, String user_id){
        JButton button = new JButton();

        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon(currentDir + iconPath);
        button.setIcon(icon);
        button.setPreferredSize(new Dimension(40, 40));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, type + " " + user_id);
                if(type.equals("chat"))
                    mainCard.show(mainContentPanel, "CHAT");
            }
        });
        return button;
    }
}
