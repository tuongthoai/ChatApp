package com.hcmus.ui.chatbox.ChatBoxHeaderButtonsAction;

import com.hcmus.utils.UserProfile;
import com.hcmus.models.GroupChatMember;
import com.hcmus.services.GChatService;
import com.hcmus.services.SpamReportService;
import com.hcmus.ui.chatbox.ChatBox;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class GroupChatSpamBtnAction implements ActionListener {
    private ChatBox parent;
    private int selectedIndices = -1;

    public GroupChatSpamBtnAction(ChatBox parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (parent.getChatId() == null) {
            JOptionPane.showMessageDialog(parent, "You are not in any group chat");
            return;
        }
        JDialog spamReportDlg = new JDialog((JFrame) SwingUtilities.getWindowAncestor(parent));
        spamReportDlg.setSize(600, 400);
        spamReportDlg.setTitle("Report Info Request");
        spamReportDlg.setLocationRelativeTo(parent);
        spamReportDlg.setLayout(new BorderLayout());

        GChatService service = GChatService.getInstance();
        List<GroupChatMember> members = service.getGroupChatMembers(parent.getChatId());
        List<String> userNames = members.stream().map(mem -> mem.getUsername()).collect(Collectors.toList());
        JList<String> userList = new JList<>(userNames.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setPreferredSize(new Dimension(400, 50));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(600, 80));

        // create a Label
        JLabel selectUserLbl = new JLabel("Select user you want to report".toUpperCase());
        selectUserLbl.setSize(new Dimension(600, 50));
        selectUserLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        // add label for the scroll list of user
        topPanel.add(selectUserLbl);
        topPanel.add(scrollPane);
        // add the top panel to the spam dialog
        spamReportDlg.add(topPanel, BorderLayout.NORTH);

        // the center of the dialog is the panel for user input
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        JLabel contentLbl = new JLabel("ENTER YOUR REPORT INFORMATION");
        contentLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(contentLbl);

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(580, 300));
        contentPanel.add(textArea);
        spamReportDlg.add(contentPanel, BorderLayout.CENTER);

        // the bottom of the report dialog is the button for submit the report or cancel
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        JButton submitBtn = new JButton("Submit");
        submitBtn.setPreferredSize(new Dimension(100, 30));
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(100, 30));

        btnPanel.add(submitBtn);
        btnPanel.add(cancelBtn);
        spamReportDlg.add(btnPanel, BorderLayout.SOUTH);

        spamReportDlg.setVisible(true);
        // add action for the JList
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Get the selected index
                    int selectedIndex = userList.getSelectedIndex();
                    selectedIndices = selectedIndex;
                    System.out.println(selectedIndices);
                }
            }
        });

        // add action for the 2 btn
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spamReportDlg.dispose();
            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndices == -1) {
                    JOptionPane.showMessageDialog(
                            parent, // Parent component (null for default)
                            "Please select user to be report", // Message to display
                            "Error", // Dialog title
                            JOptionPane.ERROR_MESSAGE // Message type (error icon)
                    );
                }

                GroupChatMember member = members.get(selectedIndices);
                String msg = textArea.getText();
                int curUserId = UserProfile.getUserProfile().getId();

                SpamReportService spamReportService = SpamReportService.getInstance();
                try {
                    boolean result = spamReportService.submitReport(curUserId, member.getUserId(), msg);
                    if (result == true) {
                        JOptionPane.showMessageDialog(
                                parent, // Parent component (null for default)
                                "SUBMIT SUCCESSFULLY", // Message to display
                                "SUBMIT STATUS", // Dialog title
                                JOptionPane.INFORMATION_MESSAGE // Message type (error icon)
                        );
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(
                            parent, // Parent component (null for default)
                            "Network error", // Message to display
                            "Error", // Dialog title
                            JOptionPane.ERROR_MESSAGE // Message type (error icon)
                    );
                    err.printStackTrace();
                }
            }
        });
        System.out.println("Spam button clicked");
    }
}
