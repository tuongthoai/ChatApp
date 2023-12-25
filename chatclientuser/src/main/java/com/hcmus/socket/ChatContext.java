package com.hcmus.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.models.ChatMessage;
import com.hcmus.observer.Subscriber;
import com.hcmus.services.ComponentIdContext;
import com.hcmus.services.EventHandlerService;
import com.hcmus.ui.chatlist.ChatListItem;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ChatContext extends WebSocketClient {
    private static ChatContext INSTANCE = null;
    private static URI webSocketUri;
    private static Map<String, String> headers;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<Integer, Subscriber> chatBoxMap = new HashMap<>();

    private ChatContext(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public static ChatContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatContext(webSocketUri, headers);
        }
        return INSTANCE;
    }

    public static ChatContext getInstance(URI _uri, Map<String, String> _headers) {
        webSocketUri = _uri;
        headers = _headers;
        if (INSTANCE == null) {
            INSTANCE = new ChatContext(webSocketUri, headers);
            try {
                INSTANCE.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    public static void setHeaders(Map<String, String> headers) {
        ChatContext.headers = headers;
    }

    public static void setWebSocketUri(URI webSocketUri) {
        ChatContext.webSocketUri = webSocketUri;
    }

    public void addObserver(Subscriber subscriber) {
        chatBoxMap.put(subscriber.getObserverId(), subscriber);
    }

    public void removeObserver(Subscriber subscriber) {
        chatBoxMap.remove(subscriber.getObserverId());
    }

    public void notify(Integer id, Object obj) {
        (chatBoxMap.get(id)).update(obj);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connection Opened");
    }

    @Override
    public void onMessage(String message) {
        ChatMessage msg = null;
        try {
            msg = mapper.readValue(message, ChatMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Network error");
        }

        if (msg != null) {
            if (msg.getMsgType() == null) {
                this.notify(msg.getGroupChatId(), msg);
            } else {
                if (msg.getMsgType().equals("SYS")) {
                    String ctx = msg.getMsgContent();
                    String[] opt = ctx.split("->");
                    if (opt.length == 2) {
                        if (opt[1].equals("MEMBER_LIST")) {
                            EventHandlerService.getInstance().notify(ComponentIdContext.MEMBER_LIST_ID, null);
                        }
                        if (opt[1].equals("CHAT_SCREEN")) {
                            EventHandlerService.getInstance().notify(ComponentIdContext.CHAT_SCREEN_ID, null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection close");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println(ex.getStackTrace());
    }
}
