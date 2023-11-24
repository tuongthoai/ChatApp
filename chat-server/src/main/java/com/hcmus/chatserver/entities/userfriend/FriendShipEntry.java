package com.hcmus.chatserver.entities.userfriend;

public class FriendShipEntry {
    private int userId;
    private int friendId;

    public FriendShipEntry() {
    }

    public FriendShipEntry(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
