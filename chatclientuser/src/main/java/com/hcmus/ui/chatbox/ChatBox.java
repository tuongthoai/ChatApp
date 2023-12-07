package com.hcmus.ui.chatbox;

import com.hcmus.UserProfile;
import com.hcmus.models.ChatMessage;
import com.hcmus.observer.Subscribe;
import com.hcmus.socket.ChatContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChatBox extends JPanel implements Subscribe {
    private static final String imageDir = System.getProperty("user.dir") + "/chatclientuser/src/main/java/com/hcmus/ui/images/";
    private Integer chatId;
    private ChatContext context;
    private JTextArea chatContent;
    private ArrayList<ChatMessage> chatMessages;
    private String chatName;
    private Map<String, String> msgHeaders;

    public ChatBox() {
        chatName = "";
        chatMessages = new ArrayList<>();

        initComponent();
    }

    public ChatBox(String chatName, Integer chatId, ChatContext context) {
        this.chatName = chatName;
        this.chatId = chatId;
        this.context = context;
        this.msgHeaders = new HashMap<>();
        msgHeaders.put("GCHAT_ID", String.valueOf(chatId));
        msgHeaders.put("USER_SEND_ID", String.valueOf(UserProfile.getUserProfile().getId()));
        this.chatMessages = new ArrayList<>();
        this.context.addObserver(this);
        initComponent();
    }

    private static JButton createButton(String text, String iconFilename) {
        JButton button = new JButton();

        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        // Load the icon
        ImageIcon icon = new ImageIcon(imageDir + iconFilename);
        button.setIcon(icon);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Add vertical spacing around the button
        int topPadding = 10;
        int bottomPadding = 10;
        int leftPadding = 10;
        int rightPadding = 10;
        button.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
        button.addActionListener(new ChatBoxAction(text));
        return button;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getUsername() {
        return chatName;
    }

    public ChatContext getContext() {
        return context;
    }

    public void setContext(ChatContext context) {
        this.context = context;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public JTextArea getChatContent() {
        return chatContent;
    }

    public void setChatContent(JTextArea chatContent) {
        this.chatContent = chatContent;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Map<String, String> getMsgHeaders() {
        return msgHeaders;
    }

    public void setMsgHeaders(Map<String, String> msgHeaders) {
        this.msgHeaders = msgHeaders;
    }

    public void initComponent() {
        setPreferredSize(new Dimension(500, 400));
        this.setLayout(new BorderLayout());

        JPanel header = createHeaderPanel();
        add(header, BorderLayout.NORTH);

        JScrollPane scrollPane = createChatContentPanel();
        add(scrollPane, BorderLayout.CENTER);

        displayChatMessage();

        JPanel footer = createFooterPanel();
        add(footer, BorderLayout.SOUTH);
    }

    public JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(500, 50));

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setOpaque(true);

        // Create a rounded image icon
        RoundImageIcon avatarImage = new RoundImageIcon(imageDir + "LogoHCMUS.jpg", 40, 40);
        JLabel userAvatar = new JLabel(avatarImage);
        userAvatar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // Create labels for chatName and status
        JLabel usernameLabel = new JLabel(chatName);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Create a panel for the status with a small green circle
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Use FlowLayout
        statusPanel.setOpaque(false);
        statusPanel.setPreferredSize(new Dimension(40, 40)); // Set preferred size

        // Create a small green circle
        JLabel statusCircle = new JLabel();
        statusCircle.setPreferredSize(new Dimension(10, 10));
        statusCircle.setBackground(Color.GREEN);
        statusCircle.setOpaque(true);

        // Add the "Active" label
        JLabel statusLabel = new JLabel("Active");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        // Add the status components to the statusPanel
        statusPanel.add(statusCircle, BorderLayout.WEST);
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // Add components to the UserPanel
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(usernameLabel);
        textPanel.add(statusPanel);
        textPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        userPanel.add(userAvatar, BorderLayout.WEST);
        userPanel.add(textPanel, BorderLayout.CENTER);

        header.add(userPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();

        JButton searchButton = createButton("Search", "search-icon.png");
        JButton deleteButton = createButton("Delete", "trash-solid.png");
        JButton infoButton = createButton("Members", "info.png");
        JButton spamButton = createButton("Spam", "spam.png");
        infoButton.addActionListener(new GroupInfoBtnAction(this));
        spamButton.addActionListener(new SpamBtnAction());

        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(infoButton);
        buttonPanel.add(spamButton);

        header.add(buttonPanel, BorderLayout.EAST);

        return header;
    }

    public JScrollPane createChatContentPanel() {
        chatContent = new JTextArea();
        chatContent.setEditable(false);
        Font customFont = new Font("Arial", Font.PLAIN, 14); // You can choose your preferred font
        chatContent.setFont(customFont);
        chatContent.setLineWrap(true);
        chatContent.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    public JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(500, 30));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 30));
        footer.add(textField, BorderLayout.WEST);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ChatBoxAction("Send", this, textField));
        footer.add(sendButton, BorderLayout.EAST);
        return footer;
    }

    public void displayChatMessage() {
        chatContent.setText(""); // Clear the existing messages before displaying
        Map<String, String> headers = null;
        for (ChatMessage message : chatMessages) {
            headers = message.getHeaders();
            if (headers.get("GCHAT_ID").equals(String.valueOf(this.chatId))) {
                chatContent.append("< Me >: " + message.getContent() + "\n");
            } else {
                chatContent.append("< " + headers.get("USER_SEND_ID") + " >: " + message.getContent() + "\n");
            }
            chatContent.setCaretPosition(chatContent.getDocument().getLength());
        }
    }

    @Override
    public int getObserverId() {
        return this.chatId;
    }

    @Override
    public void update(Object obj) {
        ChatMessage msg = (ChatMessage) obj;
        this.chatMessages.add(msg);
        displayChatMessage();
    }
}
