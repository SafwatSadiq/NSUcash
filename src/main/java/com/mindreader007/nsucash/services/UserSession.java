package com.mindreader007.nsucash.services;

import com.mindreader007.nsucash.model.User;

public class UserSession {
    private static User loggedInUser;

    public static void startSession(User user) {
        loggedInUser = user;
    }

    public static User getUser() {
        return loggedInUser;
    }

    public static void endSession() {
        loggedInUser = null;
    }

    public static void updateUser(User user){
        loggedInUser = user;
    }

    public static String getLoggedInUsername() {
        if (loggedInUser != null) {
            return loggedInUser.getUsername();
        }
        return null;
    }

}
