/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

public interface Account {

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: replace the password with new one
    void setPassword(String newPassword);

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: replace the nickname with new one
    void setNickname(String newName);

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the current password
    String getPassword();

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the current nickname
    String getNickname();

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the current username
    String getUsername();

}
