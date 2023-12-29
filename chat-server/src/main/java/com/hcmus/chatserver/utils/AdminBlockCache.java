package com.hcmus.chatserver.utils;

import java.util.ArrayList;
import java.util.List;

public class AdminBlockCache {
    private static final List<Integer> data;
    static {
        data = new ArrayList<>();
    }
    public static List<Integer> getData() {
        return data;
    }

    public static boolean isBlocked(int userId) {
        return data.contains(userId);
    }

    public static void add(int userId) {
        data.add(userId);
    }

    public static void remove(int userId) {
        data.remove(Integer.valueOf(userId));
    }
}
