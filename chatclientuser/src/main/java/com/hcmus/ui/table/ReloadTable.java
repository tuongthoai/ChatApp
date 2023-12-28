package com.hcmus.ui.table;

import com.hcmus.utils.UserProfile;
import com.hcmus.models.Friend;
import com.hcmus.models.User;
import com.hcmus.models.UserDTO;
import com.hcmus.services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReloadTable {
    public static void reloadFriendTable(Table<UserDTO> table, String status){
        UserService service = UserService.getInstance();
        List<User> listfriend = new ArrayList<>();
        List<UserDTO> result = new ArrayList<>();
        try {
            listfriend = service.findAllFriends(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User friend : listfriend){
            UserDTO userDTO = new UserDTO();

            userDTO.setId(friend.getId());
            userDTO.setUsername(friend.getUsername());
            userDTO.setFullname(friend.getName());
            if(friend.isOnline()){
                userDTO.setOnline("Online");
            } else {
                userDTO.setOnline("Offline");
            }

            if(status.equals("All") || userDTO.getOnline().equals(status))
                result.add(userDTO);
        }

        table.updateData(result);
        table.updateTable();
    }
    public static void reloadStrangerTable(Table<Friend> table){
        UserService service = UserService.getInstance();
        List<User> listNotFriend = new ArrayList<>();
        List<User> blockList = new ArrayList<>();
        List<Friend> result = new ArrayList<>();
        try {
            listNotFriend = service.findAllStrangers(UserProfile.getUserProfile().getId());
            blockList = UserService.getInstance().getAllBlockedUsers(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User stranger : listNotFriend){
            boolean isBlocked = false;

            for (User blocked : blockList) {
                if (stranger.getId() == blocked.getId()) {
                    isBlocked = true;
                    break;
                }
            }
            if (isBlocked) {
                continue;
            }
            Friend newFriend = new Friend();

            newFriend.setId(stranger.getId());
            newFriend.setUsername(stranger.getUsername());
            newFriend.setFullname(stranger.getName());
            newFriend.setSex(stranger.getSex());

            LocalDate date = UnixTimestampConverter.unix2DateTime((long) stranger.getBirthday());
            newFriend.setBirthday(date);

            result.add(newFriend);
        }

        table.updateData(result);
        table.updateTable();
    }

    public static void reloadBlockTable(Table<UserDTO> table){
        UserService service = UserService.getInstance();
        List<User> listBlocked = new ArrayList<>();
        List<UserDTO> result = new ArrayList<>();
        try {
            listBlocked = service.getAllBlockedUsers(UserProfile.getUserProfile().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(User blocked : listBlocked){
            UserDTO userDTO = new UserDTO();

            userDTO.setId(blocked.getId());
            userDTO.setUsername(blocked.getUsername());
            userDTO.setFullname(blocked.getName());
            if(blocked.isOnline()){
                userDTO.setOnline("Online");
            } else {
                userDTO.setOnline("Offline");
            }

            result.add(userDTO);
        }

        table.updateData(result);
        table.updateTable();
    }
}
