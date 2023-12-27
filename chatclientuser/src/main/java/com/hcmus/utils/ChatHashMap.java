package com.hcmus.utils;

import com.hcmus.models.ChatContentDTO;
import com.hcmus.socket.ChatContext;
import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatlayout.ChatScreen;
import com.hcmus.ui.chatlist.ChatListItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHashMap {
    private static ChatHashMap instance = null;
    private ChatContext context;
    private HashMap<ChatListItem, ChatBox> chatHashMap;
    private Map<Integer, ChatListItem> id2ChatListItem;
    private ChatScreen chatScreen;

    private ChatHashMap() {
        chatHashMap = new HashMap<>();
        id2ChatListItem = new HashMap<>();
    }

    public static ChatHashMap getInstance() {
        if (instance == null) {
            instance = new ChatHashMap();
        }
        return instance;
    }

    public void addChat(ChatListItem chatListItem, int chatId, String chatName, List<ChatContentDTO> history) {
        if (chatHashMap == null) {
            chatHashMap = new HashMap<>();
        }
        ChatBox chatBox = new ChatBox(chatName, chatId, context, history, this.chatScreen);
        chatHashMap.put(chatListItem, chatBox);
        id2ChatListItem.put(chatId, chatListItem);
    }

    public void removeChat(ChatListItem chatListItem) {
        if (chatHashMap != null) chatHashMap.remove(chatListItem);
    }

    public ChatBox getChat(ChatListItem chatListItem) {
        if (chatHashMap != null) return chatHashMap.get(chatListItem);
        return null;
    }

    public ChatContext getContext() {
        return context;
    }

    public void setContext(ChatContext context) {
        this.context = context;
    }

    public void clear() {
        chatHashMap.clear();
    }

    public void setChatScreen(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
    }

    public Map<Integer, ChatListItem> getId2ChatListItem() {
        return id2ChatListItem;
    }

    public ChatBox getChatBoxFromId(int chatId) {
        if (id2ChatListItem.containsKey(chatId)) {
            return chatHashMap.get(id2ChatListItem.get(chatId));
        }
        return null;
    }
}
