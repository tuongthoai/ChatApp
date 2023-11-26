package com.hcmus.ui.datatest;

import java.util.ArrayList;

public class DataTest {
    private ArrayList<User> userList;
    private ArrayList<UserFriend> friendList;
    private ArrayList<GroupChat> groupChatList;
    private ArrayList<GroupChatOfUser> groupChatOfUser;
    private ArrayList<GroupChatAdmin> groupChatAdmin;
    private ArrayList<GroupChatContent> groupChatContent;
    private ArrayList<GroupChatMember> groupChatMember;

    DataTest(){
        initData();
    }
    private void initData(){
        userList.add(new User("1", "usn1","Tran Nguyen J"));
        userList.add(new User("2", "usn2","Nguyen Van A"));
        userList.add(new User("3", "usn3","Le Van B"));
        userList.add(new User("4", "usn4","Ly Thi C"));
        userList.add(new User("5", "usn5","Nguyen Thi D"));
        userList.add(new User("6", "usn6","Vo Van E"));
        userList.add(new User("7", "usn7","Tang F"));
        userList.add(new User("8", "usn8","Lac G"));
        userList.add(new User("9", "usn9","Cao H"));

        friendList.add(new UserFriend("1", "3"));
        friendList.add(new UserFriend("3", "1"));
        friendList.add(new UserFriend("1", "5"));
        friendList.add(new UserFriend("5", "1"));
        friendList.add(new UserFriend("1", "7"));
        friendList.add(new UserFriend("7", "1"));
        friendList.add(new UserFriend("2", "3"));
        friendList.add(new UserFriend("3", "2"));
        friendList.add(new UserFriend("7", "3"));
        friendList.add(new UserFriend("3", "7"));

        groupChatList.add(new GroupChat("1", "Group 1"));
        groupChatList.add(new GroupChat("2", "Group 2"));
        groupChatList.add(new GroupChat("3", "Group 3"));
        groupChatList.add(new GroupChat("4", "Group 4"));

        groupChatOfUser.add(new GroupChatOfUser("1","1"));
        groupChatOfUser.add(new GroupChatOfUser("2","1"));
        groupChatOfUser.add(new GroupChatOfUser("3","1"));
        groupChatOfUser.add(new GroupChatOfUser("4","1"));
        groupChatOfUser.add(new GroupChatOfUser("2","2"));
        groupChatOfUser.add(new GroupChatOfUser("3","2"));
        groupChatOfUser.add(new GroupChatOfUser("2","3"));
        groupChatOfUser.add(new GroupChatOfUser("3","3"));
        groupChatOfUser.add(new GroupChatOfUser("4","4"));
        groupChatOfUser.add(new GroupChatOfUser("1","4"));
        groupChatOfUser.add(new GroupChatOfUser("4","4"));

        groupChatAdmin.add(new GroupChatAdmin("1","1"));
        groupChatAdmin.add(new GroupChatAdmin("1","2"));
        groupChatAdmin.add(new GroupChatAdmin("3","2"));
        groupChatAdmin.add(new GroupChatAdmin("3","4"));

        groupChatContent.add(new GroupChatContent("1","1","Hello"));
        groupChatContent.add(new GroupChatContent("1","2","Hi"));
        groupChatContent.add(new GroupChatContent("1","3","How are you?"));
        groupChatContent.add(new GroupChatContent("1","4","I am fine"));
        groupChatContent.add(new GroupChatContent("1","1","What are you doing?"));
        groupChatContent.add(new GroupChatContent("1","2","I am doing homework"));
        groupChatContent.add(new GroupChatContent("2","2","Hello"));
        groupChatContent.add(new GroupChatContent("2","3","Hi"));
        groupChatContent.add(new GroupChatContent("2","2","How are you?"));
        groupChatContent.add(new GroupChatContent("2","3","I am fine"));
        groupChatContent.add(new GroupChatContent("2","2","What are you doing?"));
        groupChatContent.add(new GroupChatContent("2","3","I am doing homework"));
        groupChatContent.add(new GroupChatContent("3","2","Hello"));
        groupChatContent.add(new GroupChatContent("3","3","Hi"));
        groupChatContent.add(new GroupChatContent("3","2","How are you?"));
        groupChatContent.add(new GroupChatContent("3","3","I am fine"));
        groupChatContent.add(new GroupChatContent("4","1","What are you doing?"));
        groupChatContent.add(new GroupChatContent("4","4","I am doing homework"));


        groupChatMember.add(new GroupChatMember("1","1"));
        groupChatMember.add(new GroupChatMember("1","2"));
        groupChatMember.add(new GroupChatMember("1","3"));
        groupChatMember.add(new GroupChatMember("1","4"));
        groupChatMember.add(new GroupChatMember("2","2"));
        groupChatMember.add(new GroupChatMember("2","3"));
        groupChatMember.add(new GroupChatMember("3","2"));
        groupChatMember.add(new GroupChatMember("3","3"));
        groupChatMember.add(new GroupChatMember("3","4"));
        groupChatMember.add(new GroupChatMember("4","1"));
        groupChatMember.add(new GroupChatMember("4","4"));
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<UserFriend> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<UserFriend> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<GroupChat> getGroupChatList() {
        return groupChatList;
    }

    public void setGroupChatList(ArrayList<GroupChat> groupChatList) {
        this.groupChatList = groupChatList;
    }

    public ArrayList<GroupChatOfUser> getGroupChatOfUser() {
        return groupChatOfUser;
    }

    public void setGroupChatOfUser(ArrayList<GroupChatOfUser> groupChatOfUser) {
        this.groupChatOfUser = groupChatOfUser;
    }

    public ArrayList<GroupChatAdmin> getGroupChatAdmin() {
        return groupChatAdmin;
    }

    public void setGroupChatAdmin(ArrayList<GroupChatAdmin> groupChatAdmin) {
        this.groupChatAdmin = groupChatAdmin;
    }

    public ArrayList<GroupChatContent> getGroupChatContent() {
        return groupChatContent;
    }

    public void setGroupChatContent(ArrayList<GroupChatContent> groupChatContent) {
        this.groupChatContent = groupChatContent;
    }

    public ArrayList<GroupChatMember> getGroupChatMember() {
        return groupChatMember;
    }

    public void setGroupChatMember(ArrayList<GroupChatMember> groupChatMember) {
        this.groupChatMember = groupChatMember;
    }
}
