package com.hcmus.ui.chatbox;

import com.hcmus.models.GroupChatMember;
import com.hcmus.models.User;
import com.hcmus.observer.Subscriber;
import com.hcmus.services.ComponentIdContext;
import com.hcmus.services.EventHandlerService;
import com.hcmus.services.GChatService;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupInfoDialog extends JDialog implements Subscriber {
    private ChatBox parent;

    public GroupInfoDialog() {
    }

    public GroupInfoDialog(ChatBox parent) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), "Group Information", true);
        this.parent = parent;
        GChatService service = GChatService.getInstance();
        if (parent.getChatId() != null) {
            List<GroupChatMember> members = service.getGroupChatMembers(parent.getChatId());
            try {
                List<User> admins = service.findAllAdmins(parent.getChatId());
                Set<Integer> admins_id = new HashSet<>();
                for (User usr : admins) {
                    admins_id.add(usr.getId());
                }
                for (GroupChatMember member : members) {
                    if (admins_id.contains(member.getUserId())) {
                        member.setRole("ROLE_ADMIN");
                    } else {
                        member.setRole("ROLE_USER");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            EventHandlerService eventHandlerService = EventHandlerService.getInstance();
            MemberList memberList = new MemberList(members, parent.getChatId());
            eventHandlerService.addObserver(memberList);

            setSize(300, 400);
            setLocationRelativeTo(parent);
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            RenameField renameField = new RenameField(parent);
            add(renameField);
            add(new JSeparator());
            // add a label
            JLabel memberLabel = new JLabel("Members");
            memberLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            memberLabel.setFont(memberLabel.getFont().deriveFont(16f));
            memberLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(memberLabel);
            add(memberList);
        }
    }

    @Override
    public int getObserverId() {
        return ComponentIdContext.CLOSE_GROUP_INFO_DIALOG;
    }

    @Override
    public void update(Object obj) {
        this.dispose();
    }
}
