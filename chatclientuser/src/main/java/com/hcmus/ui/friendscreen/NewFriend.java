package com.hcmus.ui.friendscreen;

import com.hcmus.ui.datatest.DataTest;
import com.hcmus.ui.datatest.User;
import com.hcmus.ui.datatest.UserFriend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewFriend extends JPanel {
    private DataTest data;
    private String user_id;
    private CardLayout mainCard;
    private JPanel mainContentPanel;
    public NewFriend(DataTest data, CardLayout mainCard, JPanel mainContentPanel){
        this.mainCard = mainCard;
        this.data = data;
        this.user_id = "1";
        this.mainContentPanel = mainContentPanel;

        ArrayList<User> listNotFriend = getListNotFriend(data.getFriendList(), data.getUserList());

        init(listNotFriend);
    }
    public void init(ArrayList<User> listNotFriend){
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Friend Suggestions");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font fontText = new Font("Serif", Font.PLAIN, 18);

        for(User notFriend : listNotFriend){
            JLabel nameLabel = new JLabel(notFriend.getFullname());
            nameLabel.setFont(fontText);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weightx = 100;
            listPanel.add(nameLabel, gbc);

            JButton addButton = createButton(notFriend.getUserId());
            gbc.gridx++;
            gbc.weightx = 1;
            listPanel.add(addButton, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    private ArrayList<User> getListNotFriend(ArrayList<UserFriend> userFriends, ArrayList<User> users){
        ArrayList<User> res = new ArrayList<>();
        ArrayList<String> friend_id = new ArrayList<>();

        for(UserFriend userFriend : userFriends){
            if(userFriend.getUserId().equals(this.user_id)){
                friend_id.add(userFriend.getFriendId());
            }
        }

        for(User user : users){
            if(!friend_id.contains(user.getUserId())){
                res.add(user);
            }
        }
        return res;
    }
    private JButton createButton(String user_id){
        JButton button = new JButton("ADD");
        button.setFont(new Font("Serif", Font.PLAIN, 18));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        button.setPreferredSize(new Dimension(70, 25));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "add " + user_id);
            }
        });
        return button;
    }
}
