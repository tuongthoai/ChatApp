package com.hcmus;

import com.hcmus.models.User;

public final class UserProfile {
    private static User user;
    private UserProfile() {}
    public static User getUserProfile() {
        return user;
    }
    public static void setUserProfile(User user) {
        if (UserProfile.user == null) {
            UserProfile.user = new User(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getSex(), user.getAddress(), user.getBirthday(), user.getCreatedTime(), user.isOnline(), user.isBlocked());
        }
    }

    public static void clearUserProfile() {
        UserProfile.user = null;
    }
}
