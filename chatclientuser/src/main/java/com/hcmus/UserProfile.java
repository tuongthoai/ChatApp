package com.hcmus;

import com.hcmus.models.User;

public final class UserProfile {
    private static User user = null;
    private UserProfile() {}
    public static User getUserProfile() {
        return user;
    }
    public static void setUserProfile(User user) {
        if (UserProfile.user == null) {
            UserProfile.user = user;
        }
    }
    public static void removeUserProfile() {
        UserProfile.user = null;
    }
}
