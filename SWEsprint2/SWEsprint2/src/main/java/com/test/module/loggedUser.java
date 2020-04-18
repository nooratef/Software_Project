package com.test.module;

import java.util.ArrayList;
import java.util.List;

public class loggedUser {
    private static user activeUser = null;

    public loggedUser() {

    }

    public static user getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(user activeUser) {
        loggedUser.activeUser = activeUser;
    }
}
