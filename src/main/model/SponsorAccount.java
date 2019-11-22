/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

public class SponsorAccount extends AbstractAccount{

    public SponsorAccount(String username, String nickname, String password) {
        super(nickname, password, username);
    }

    // EFFECTS: add "<Ad> " to the beginning of the content,
    // then add the content to the list of the posts
//    @Override
//    public void publishMoment(String content) {
//        moments.add(new Moment("<Ad> " + content, this));
//        setChanged();
//        notifyObservers();
//    }
}
