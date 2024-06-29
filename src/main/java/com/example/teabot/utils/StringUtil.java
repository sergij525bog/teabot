package com.example.teabot.utils;

public class StringUtil {
    public static boolean isStartCommand(String data) {
        return "/start".equals(data) || "/start@YouAreTheTea_bot".equals(data);
    }
}
