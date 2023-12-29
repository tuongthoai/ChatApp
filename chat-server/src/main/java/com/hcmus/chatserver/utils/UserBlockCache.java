package com.hcmus.chatserver.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserBlockCache {
    private static final Map<Integer, List<Integer>> data;
    static {
        data = new java.util.concurrent.ConcurrentHashMap<>();
    }
    public static Map<Integer, List<Integer>> getData() {
        return data;
    }
    public static List<Integer> getBlockList(int userId) {
        return data.getOrDefault(userId, Collections.emptyList());
    }
    public static void add(int userId, int blockedId) {
        List<Integer> blockList = data.getOrDefault(userId, new java.util.concurrent.CopyOnWriteArrayList<>());
        blockList.add(blockedId);
        data.put(userId, blockList);
    }
    public static void remove(int userId, int blockedId) {
        List<Integer> blockList = data.getOrDefault(userId, new java.util.concurrent.CopyOnWriteArrayList<>());
        blockList.remove(Integer.valueOf(blockedId));
        data.put(userId, blockList);
    }

    public static boolean isBlocked(int userId, int blockedId) {
        return data.getOrDefault(userId, Collections.emptyList()).contains(blockedId) || data.getOrDefault(blockedId, Collections.emptyList()).contains(userId);
    }
}
