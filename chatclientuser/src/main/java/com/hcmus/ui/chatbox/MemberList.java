package com.hcmus.ui.chatbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.GroupChat;
import com.hcmus.utils.UserProfile;
import com.hcmus.models.ClientChatMessage;
import com.hcmus.models.GroupChatMember;
import com.hcmus.models.User;
import com.hcmus.observer.Subscriber;
import com.hcmus.services.ComponentIdContext;
import com.hcmus.services.GChatService;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatbox.MemberListAction.AddMemberListAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MemberList extends JPanel implements Subscriber {
    private JScrollPane mainPanel;
    private List<GroupChatMember> members;
    private int chatId;

    public MemberList(List<GroupChatMember> members, Integer chatId) {
        this.members = members;
        this.chatId = chatId;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(createMemberScrollPanel(this.members, gbc), gbc);
    }

    public JScrollPane createMemberScrollPanel(List<GroupChatMember> members, GridBagConstraints gbc) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.ipadx = 2;
        gbc.insets = new Insets(5, 5, 5, 5);


        JButton addNewMemberBtn = new JButton("Add new member");
        addNewMemberBtn.setPreferredSize(new Dimension(50, 30));
        addNewMemberBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        addNewMemberBtn.addActionListener(new AddMemberListAction(this));
        add(addNewMemberBtn, gbc);

        gbc.gridy = 1;
        int n = members.size();
        for (int i = 0; i < n; i++) {
            gbc.gridy = i * 2; // Adjusting gridy to skip a row for JSeparator
            panel.add(new MemberCard(members.get(i).getUsername(), members.get(i).getRole(), i, this), gbc);

            if (i < n - 1) {
                gbc.gridy = i * 2 + 1;
                gbc.weightx = 1.0; // Make the separator expand horizontally
                panel.add(new JSeparator(), gbc);
                gbc.weightx = 0.0; // Reset weightx
            }
        }

        mainPanel = new JScrollPane(panel);
        mainPanel.setPreferredSize(new Dimension(200, 400));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        return mainPanel;
    }

    public void updateMemberList() {
        removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        add(createMemberScrollPanel(this.members, constraints), constraints);
        revalidate();
        repaint();
    }

    @Override
    public int getObserverId() {
        return ComponentIdContext.MEMBER_LIST_ID;
    }

    @Override
    public void update(Object obj) {
        this.members = GChatService.getInstance().getGroupChatMembers(chatId);
        GChatService service = GChatService.getInstance();
        try {
            List<User> admins = service.findAllAdmins(this.chatId);
            for (GroupChatMember member : members) {
                for (User user : admins) {
                    if (user.getId() == member.getUserId()) {
                        member.setRole("ADMIN");
                    } else {
                        member.setRole("USER");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateMemberList();
    }

    public List<GroupChatMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupChatMember> members) {
        this.members = members;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    private class MemberCard extends JPanel {
        private final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 12);
        private final MemberList parent;
        private final int order;

        public MemberCard(String name, String role, int order, MemberList par) {
            this.order = order;
            this.parent = par;
            setBackground(Color.WHITE);
            setLayout(new GridBagLayout());

            JPanel subPanel = new JPanel(new GridLayout(2, 1, 5, 10));
            subPanel.setBackground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();

            // Name label
            JLabel nameLabel = createLabel(name.toUpperCase());
            subPanel.add(nameLabel, gbc);

            // Role label
            JLabel roleLabel = createLabel(role);
            roleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
            subPanel.add(roleLabel, gbc);

            // Button panel
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            buttonPanel.setBackground(Color.WHITE);

            // Remove button
            JButton removeBtn = createButton("Remove");
            removeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // check author
                    try {
                        List<User> admins = GChatService.getInstance().findAllAdmins(parent.getChatId());
                        boolean isAdmin = false;
                        for (User user : admins) {
                            if (user.getId() == UserProfile.getUserProfile().getId()) {
                                isAdmin = true;
                                break;
                            }
                        }
                        if (!isAdmin) {
                            JOptionPane.showMessageDialog(mainPanel, "You are not authorized", "Infomation", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    // check if member of the group is <= 2
                    final boolean[] deleteGroup = {false};
                    int memCnt = 0;
                    try {
                        memCnt = GChatService.getInstance().countMembers(parent.getChatId());
                        if (memCnt <= 2) {
                            JDialog confirmDltDlg = new JDialog();
                            confirmDltDlg.setSize(400, 300);
                            confirmDltDlg.setTitle("Confirm");
                            confirmDltDlg.setModal(true);
                            confirmDltDlg.setLayout(new BorderLayout());

                            JTextField textField = new JTextField("Remove this user will delete group. Are you wish to continue?");
                            confirmDltDlg.add(textField, BorderLayout.CENTER);

                            JPanel btnPnl = new JPanel();
                            JButton confirmBtn = new JButton("Confirm");
                            JButton cancelBtn = new JButton("Cancel");
                            btnPnl.add(confirmBtn);
                            btnPnl.add(cancelBtn);

                            cancelBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    confirmDltDlg.dispose();
                                }
                            });

                            confirmBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    deleteGroup[0] = true;
                                }
                            });

                            confirmDltDlg.setVisible(true);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                mainPanel,
                                "Network Error",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        ex.printStackTrace();
                    }

                    // if is admin then be able to remove member
                    boolean result = true;
                    try {
                        result = GChatService
                                .getInstance()
                                .removeMemberFromGroup(
                                        parent.getChatId(),
                                        parent.getMembers().get(order).getUserId()
                                );
                        ClientChatMessage sysUpdateMsg = new ClientChatMessage();
                        sysUpdateMsg.setMsgType("SYS");
                        sysUpdateMsg.setMsgContent("UPDATE->MEMBER_LIST");
                        sysUpdateMsg.setGroupChatId(parent.getChatId());
                        ChatContext.getInstance().send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                mainPanel,
                                "Network Error",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        ex.printStackTrace();
                    }

                }
            });
            buttonPanel.add(removeBtn);

            // Change role button
            JButton changeRoleBtn = createButton("Change");
            changeRoleBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // check author
                    try {
                        if (!GChatService.getInstance().isGroupAdmin(parent.getChatId(), UserProfile.getUserProfile().getId())) {
                            JOptionPane.showMessageDialog(mainPanel, "You are not authorized", "Infomation", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    // if is admin then be able to remove member
                    boolean result = true;
                    try {
                        result = GChatService
                                .getInstance()
                                .updateGroupMemberRole(
                                        parent.getChatId(),
                                        parent.getMembers().get(order).getUserId(),
                                        (role.equals("ADMIN") ? 2 : 1)
                                );
                        ClientChatMessage sysUpdateMsg = new ClientChatMessage();
                        sysUpdateMsg.setMsgType("SYS");
                        sysUpdateMsg.setMsgContent("UPDATE->MEMBER_LIST");
                        sysUpdateMsg.setGroupChatId(parent.getChatId());
                        ChatContext.getInstance().send((new ObjectMapper()).writeValueAsString(sysUpdateMsg));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                mainPanel,
                                "Network Error",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        ex.printStackTrace();
                    }
                }
            });
            buttonPanel.add(changeRoleBtn);

            // Add the button panel to subPanel
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.PAGE_END;
            gbc.insets = new Insets(0, 20, 0, 0);
            add(buttonPanel, gbc);

            subPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.PAGE_START;
            gbc.insets = new Insets(5, 0, 0, 20);
            add(subPanel, gbc);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(LABEL_FONT);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            return label;
        }

        private JButton createButton(String text) {
            JButton button = new JButton(text);
            button.setFont(LABEL_FONT);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            return button;
        }
    }
}
