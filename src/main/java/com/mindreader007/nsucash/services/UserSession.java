package com.mindreader007.nsucash.services;

public class UserSession {
    private static String username;

    public static void startSession(String user) {
        username = user;
    }

    public static String getUsername() {
        return username;
    }

    public static void endSession() {
        username = null;
    }
}
