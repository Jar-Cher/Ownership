package org.example.models;

import java.util.HashSet;
import java.util.Set;

public class User implements Member {

    // Must be unique
    private String username;
    private String email;
    private static Set<String> users = new HashSet<>();

    public User(String username) {
        this.username = username;
        users.add(username);
    }

    @Override
    public void requestReview() {
        System.out.println(username + " is asked for review");
    }
}
