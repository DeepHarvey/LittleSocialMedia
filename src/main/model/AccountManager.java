/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

import main.model.Exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private List<AbstractAccount> accounts;
    private AbstractAccount currentAccount;
    private FileIO fileIO;
    private static AccountManager instance;

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    private AccountManager() {
        this.accounts = new ArrayList<>();
        this.currentAccount = null;
        this.fileIO = new FileIO("./data/file.txt");
    }

    public void registerAccount(boolean isUser, String username, String password, String nickname) throws EmptyStringException {
        if (username.equals("")) {
            throw new UsernameEmptyException("Username should not be empty");
        }
        if (password.equals("")) {
            throw new PasswordEmptyException("Username should not be empty");
        }
        if (nickname.equals("")) {
            throw new NicknameEmptyException("Username should not be empty");
        }
        AbstractAccount account;
        if (isUser) {
            account = new UserAccount(username, nickname, password);
        } else {
            account = new SponsorAccount(username, nickname, password);
        }
        accounts.add(account);
    }

    public void load() {
        accounts = fileIO.read();
    }

    public void save() {
        fileIO.write(accounts);
    }

    public void publishMoment(String content) throws AccountNullException {
        if (currentAccount != null) {
            currentAccount.publishMoment(content);
        } else {
            throw new AccountNullException();
        }
    }

    public void addFriend(String username) throws AccountNullException, AccountNotExistException {
        if (currentAccount != null) {
            AbstractAccount account = findAccount(username);
            if (account != null && !account.equals(currentAccount)){
                currentAccount.addFriend(account);
            } else {
                throw new AccountNotExistException();
            }
        } else {
            throw new AccountNullException();
        }
    }

    public void addLike(Moment moment) throws AccountNullException {
        if (currentAccount != null) {
            moment.addLike(currentAccount);
        } else {
            throw new AccountNullException();
        }
    }

    public void cancelLike(Moment moment) throws AccountNullException {
        if (currentAccount != null) {
            moment.cancelLike(currentAccount);
        } else {
            throw new AccountNullException();
        }
    }

    public List<Moment> getMoments() throws AccountNullException {
        if (currentAccount != null) {
            return currentAccount.getMoments();
        } else {
            throw new AccountNullException();
        }
    }

    public List<Moment> getFriendMoments() throws AccountNullException {
        if (currentAccount != null) {
            List<Moment> moments = new ArrayList<>();
            for (AbstractAccount friend: currentAccount.getFriends()) {
                moments.addAll(friend.getMoments());
            }
            return moments;
        } else {
            throw new AccountNullException();
        }
    }

    public boolean login(String username, String password) {
        for (AbstractAccount account: accounts) {
            if (username.equals(account.getUsername()) && password.equals(account.getPassword())) {
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentAccount = null;
    }

    private AbstractAccount findAccount(String username) {
        for (AbstractAccount account: accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public int getNotification() {
        return currentAccount.getNumOfNotifications();
    }
}
