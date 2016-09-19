package no.ntnu.iie.stud.webapp.entities;

/**
 * Created by Audun on 15.09.2016.
 */
public class User {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
