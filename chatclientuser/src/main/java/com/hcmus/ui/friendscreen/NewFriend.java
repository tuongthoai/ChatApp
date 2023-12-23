package com.hcmus.ui.friendscreen;

import com.hcmus.UserProfile;
import com.hcmus.models.UserDTO;
import com.hcmus.services.UserService;
import com.hcmus.ui.datatest.DataTest;
import com.hcmus.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

        List<UserDTO> listNotFriend = getListNotFriend();

        init(listNotFriend);
    }
    public void init(List<UserDTO> listNotFriend){
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

        for(UserDTO notFriend : listNotFriend){
            JLabel nameLabel = new JLabel(notFriend.getFullname());
            nameLabel.setFont(fontText);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.weightx = 100;
            listPanel.add(nameLabel, gbc);

            JButton addButton = createButton(String.valueOf(notFriend.getId()));
            gbc.gridx++;
            gbc.weightx = 1;
            listPanel.add(addButton, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    private List<UserDTO> getListNotFriend(){
        UserService service = UserService.getInstance();
        List<User> listNotFriend = new ArrayList<>();
        try {
            listNotFriend = service.findAllStrangers(UserProfile.getUserProfile().getId());
            List<UserDTO> listNotFriendDTO = new ArrayList<>();
            for (User user : listNotFriend) {
                listNotFriendDTO.add(new UserDTO(user.getId(), user.getUsername(), user.getName(), user.isOnline() ? "Online" : "Offline"));

            }
            return listNotFriendDTO;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
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
