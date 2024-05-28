package org.example.models;

import java.util.HashSet;
import java.util.Set;

public class Team implements Member {

    private final Set<User> members = new HashSet<>();
    private String teamname;

    public Team(String teamname) {
        this.teamname = teamname;
    }

    @Override
    public void requestReview() {
        for (User member : members) {
            member.requestReview();
        }
    }

    public void addMemeber(User user) {
        members.add(user);
    }
}
