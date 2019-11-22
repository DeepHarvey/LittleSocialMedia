/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.ui;

import javafx.geometry.Pos;
import main.model.AbstractAccount;
import main.model.AccountManager;
import main.model.Exceptions.*;
import main.model.Moment;

import java.util.List;
import java.util.Scanner;

public class demo {
    public static AccountManager accountManager;

    public static void main(String[] args) {
        accountManager = AccountManager.getInstance();
        accountManager.load();
//        accountManager.save();
        new LoginFrame();
    }

    private static void commandInteractive() {
        accountManager = AccountManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter command here:");
        String inputString = scanner.nextLine();
        while (!inputString.toLowerCase().equals("quit")) {
            switch (inputString) {
                case "new account":
                    System.out.println("Please enter 1 for normal account or 2 for sponsor account:");
                    String choice = scanner.nextLine();
                    boolean isUser = choice.equals("1");
                    if ((!choice.equals("1")) && (!choice.equals("2"))) {
                        System.out.println("Not supported Account!");
                        break;
                    }
                    System.out.print("Please enter the username:");
                    String username = scanner.nextLine();
                    System.out.print("Please enter the password:");
                    String password = scanner.nextLine();
                    System.out.print("Please enter the nickname:");
                    String nickname = scanner.nextLine();
                    try {
                        accountManager.registerAccount(isUser, username, password, nickname);
                    } catch (UsernameEmptyException e) {
                        System.out.println("Username is empty!");
                    } catch (PasswordEmptyException e) {
                        System.out.println("password is empty!");
                    } catch (Exception e) {
                        System.out.println("nickname is empty!");
                    }
                    break;
                case "load":
                    accountManager.load();
                    break;
                case "post":
                    post(scanner);
                    break;
                case "add friend":
                    System.out.println("Please enter your friend username:");
                    String name = scanner.nextLine();
                    try {
                        accountManager.addFriend(name);
                    } catch (AccountNullException e) {
                        System.out.println("Please login first!");
                    } catch (AccountNotExistException e) {
                        System.out.println("The account with the username does not exist!");
                    }
                    break;
                case "moments":
                    try {
                        List<Moment> moments = accountManager.getFriendMoments();
                        if (moments.size() == 0) {
                            System.out.println( "Opps, It seems like you don't have friends.");
                            break;
                        }
                        System.out.println("Moments:");
                        int i = 1;
                        for (Moment moment: moments) {
                            System.out.println(i + " " + moment.toString());
                            ++ i;
                        }
                        System.out.println("Please enter the ordered number of moment you want to like. (0 means they are boring)");
                        int number = scanner.nextInt();
                        if (number > i) {
                            System.out.println("The entered number is out of index!");
                        } else {
                            if (number == 0)
                                break;
                            accountManager.addLike(moments.get(number - 1));
                            System.out.println("done!");
                        }
                    } catch (AccountNullException e) {
                        System.out.println("Please login first!");
                    }
                    break;
                case "show":
                    try {
                        List<Moment> moments = accountManager.getMoments();
                        System.out.println("Your posts:");
                        for (Moment moment: moments) {
                            System.out.println("    " + moment.getContent() +
                                    "   Likes: ");
                            for (AbstractAccount account : moment.getLikeList()) {
                                System.out.print(account.getNickname() + " ");
                            }
                            System.out.println(".");
                        }
                    } catch (AccountNullException e) {
                        System.out.println("Please login first!");
                    }
                    break;
                case "login":
                    System.out.print("Please enter the username:");
                    username = scanner.nextLine();
                    System.out.print("Please enter the password:");
                    password = scanner.nextLine();
                    if (accountManager.login(username, password)) {
                        System.out.println("Successfully log in!");
                        System.out.println("You got " + accountManager.getNotification() + " new notifications!");
                    } else {
                        System.out.println("Login fails. Username or password is incorrect.");
                    }
                    break;
                default:
                    System.out.println("Command is not supported yet.");

            }
            System.out.println("Please enter command here:");
            inputString = scanner.nextLine();
        }
        accountManager.save();
    }

    private static void post(Scanner scanner) {
        System.out.println("Please enter your moment:");
        String content = scanner.nextLine();
        try {
            accountManager.publishMoment(content);
        } catch (AccountNullException e) {
            System.out.println("Please login first!");
        }
    }
}
