/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.ui;

import main.model.AccountManager;
import main.model.Exceptions.AccountNotExistException;
import main.model.Exceptions.AccountNullException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationPanel extends JPanel implements ActionListener {

    private MomentFrame parentFrame;
    private AccountManager accountManager;
    private JButton friendButton;
    private JButton postButton;
    private JButton logoutButton;

    public NavigationPanel(MomentFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
        accountManager = AccountManager.getInstance();
        setSize(new Dimension(400, 100));
        setLayout(new FlowLayout());
        friendButton = new JButton("Add friend");
        friendButton.setActionCommand("add friend");
        friendButton.addActionListener(this);
        postButton = new JButton("post");
        postButton.setActionCommand("post");
        postButton.addActionListener(this);
        logoutButton = new JButton("log out");
        logoutButton.setActionCommand("logout");
        logoutButton.addActionListener(this);
        add(friendButton);
        add(postButton);
        add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add friend")) {
            String username = JOptionPane.showInputDialog(this, "Enter your friend's username:", "Add Friend", JOptionPane.QUESTION_MESSAGE);
            try {
                accountManager.addFriend(username);
                JOptionPane.showMessageDialog(this, "Added successfully!", "lol", JOptionPane.INFORMATION_MESSAGE);
                parentFrame.update();
            } catch (AccountNullException ex) {
                System.out.println("No account is logged in");
            } catch (AccountNotExistException ex) {
                System.out.println("Friend username does not exist");
            }
        } else if (e.getActionCommand().equals("logout")) {
            accountManager.logout();
            parentFrame.dispose();
            new LoginFrame();
        } else if (e.getActionCommand().equals("post")) {
            new PostFrame(parentFrame);
        }
    }
}
