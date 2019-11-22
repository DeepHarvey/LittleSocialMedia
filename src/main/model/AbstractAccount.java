/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

import java.util.*;

public abstract class AbstractAccount extends Observable implements Account, Interactive, Observer {
    private String username;
    private String nickname;
    private String password;
    private Map<String, AbstractAccount> friends;
    protected List<Moment> moments;
    private int numOfNotifications;

    public AbstractAccount(String nickname, String password, String username) {
        this.nickname = nickname;
        this.password = password;
        moments = new ArrayList<>();
        friends = new HashMap<>();
        this.username = username;
        numOfNotifications = 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        numOfNotifications ++;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: replace the password with new one
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: replace the nickname with new one
    public void setNickname(String newName) {
        this.nickname = newName;
    }

    public void addFriend(AbstractAccount friendAccount) {
        if (!hasFriend(friendAccount)) {
            addObserver(friendAccount);
            friends.put(friendAccount.username, friendAccount);
            friendAccount.addFriend(this);
        }
    }

    private boolean hasFriend(AbstractAccount friendAccount) {
        return friends.containsKey(friendAccount.username);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the current password
    public String getPassword() {
        return password;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the current nickname
    public String getNickname() {
        return nickname;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the current username
    public String getUsername() {
        return username;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }

    public int getNumOfNotifications() {
        return numOfNotifications;
    }

    public Collection<AbstractAccount> getFriends() {
        return friends.values();
    }

    public void setFriends(Map<String, AbstractAccount> friends) {
        this.friends = friends;
    }

    public void publishMoment(String content) {
        moments.add(new Moment(content, this));
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAccount account = (AbstractAccount) o;
        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
