package com.polchlopek.praca.magisterska.DTO;

import com.polchlopek.praca.magisterska.entity.User;

public class LoggedInUser {
    private static LoggedInUser ourInstance = new LoggedInUser();

    public static LoggedInUser getInstance() {
        return ourInstance;
    }

    private User loggedInUSer;

    private LoggedInUser() {
    }

    public User getLoggedInUSer() {
        return loggedInUSer;
    }

    public void setLoggedInUSer(User loggedInUSer) {
        this.loggedInUSer = loggedInUSer;
    }
}
