/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

public class UserAccount extends AbstractAccount {

    // REQUIRES: username/nickname/password cannot be empty
    // MODIFIES: this
    // EFFECTS: create a new user account with username, nickname and password
    public UserAccount(String username, String nickname, String password) {
        super(nickname, password, username);
    }

    // EFFECTS: add the content to the list of the posts
//    @Override
//    public void publishMoment(String content) {
//        moments.add(new Moment(content, this));
//        setChanged();
//        notifyObservers();
//    }
}
