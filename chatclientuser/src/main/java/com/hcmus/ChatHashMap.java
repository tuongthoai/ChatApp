package com.hcmus;

import com.hcmus.ui.chatbox.ChatBox;
import com.hcmus.ui.chatlist.ChatListItem;

import java.util.HashMap;
public class ChatHashMap {
    private static HashMap<ChatListItem, ChatBox> chatHashMap;
    private ChatHashMap() {
        chatHashMap = new HashMap<>();
    }
    public static void addChat(ChatListItem chatListItem, int chatId, String chatName) {
        if (chatHashMap == null) {
            chatHashMap = new HashMap<>();
        }
        ChatBox chatBox = new ChatBox(chatName, chatId);
        chatHashMap.put(chatListItem, chatBox);
    }

    public static void removeChat(ChatListItem chatListItem) {
        if (chatHashMap != null) chatHashMap.remove(chatListItem);
    }

    public static ChatBox getChat(ChatListItem chatListItem) {
        if (chatHashMap != null) return chatHashMap.get(chatListItem);
        return null;
    }
}
