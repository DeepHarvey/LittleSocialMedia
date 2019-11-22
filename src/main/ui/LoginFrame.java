/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.ui;

import main.model.AbstractAccount;
import main.model.AccountManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginFrame extends JFrame implements ActionListener, KeyListener {

    private static final Font labelFont = new Font ("Georgia", Font.BOLD, 14);

    private JTextField usernameField;
    private JTextField passwordField;
    private AccountManager accountManager = AccountManager.getInstance();
    private JButton loginButton;
    private JButton signUpButton;

    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 20, 13, 20) );
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout(0,1,1,0));

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setLayout(new GridLayout(0,1,1,0));
        panel1.setBorder(new EmptyBorder(30, 50, 0, 50));

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.LIGHT_GRAY);
        panel2.setLayout(new GridLayout(0,1,1,2));
        panel2.setBorder(new EmptyBorder(0, 50, 0, 50));

        JLabel label = new JLabel("Little Social media", JLabel.CENTER);
        label.setFont(new Font("Georgia", Font.BOLD, 28));
        JLabel usernameLabel = new JLabel("Username", JLabel.CENTER);
        label.setFont(labelFont);
        label.setForeground(Color.getHSBColor(0, 100, 0 ));

        JLabel passwordLabel = new JLabel("Password", JLabel.CENTER);
        label.setFont(labelFont);
        label.setForeground(Color.getHSBColor(0, 100, 0 ));

        JLabel blankLabel = new JLabel("", JLabel.CENTER);
        label.setFont(labelFont);
        label.setForeground(Color.getHSBColor(0, 100, 0 ));
        loginButton = new JButton("login");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);
        signUpButton = new JButton("Sign up");
        signUpButton.setActionCommand("sign-up");
        signUpButton.addActionListener(this);

        usernameField = new JTextField();
        passwordField = new JTextField();

        panel1.add(label);
        panel2.add(blankLabel);
        panel2.add(usernameLabel);
        panel2.add(usernameField);
        panel2.add(passwordLabel);
        panel2.add(passwordField);
        panel2.add(blankLabel);
        panel2.add(loginButton);
        panel2.add(signUpButton);

        add(panel1);
        add(panel2);

        addKeyListener(this);
        usernameField.addKeyListener(this);
        passwordField.addKeyListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (e.getActionCommand().equals("login")) {
            if (username.equals("")) {
                JOptionPane.showMessageDialog(this, "Username should not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.equals("")) {
                JOptionPane.showMessageDialog(this, "Password should not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean result = accountManager.login(username, password);

            if (!result) {
                JOptionPane.showMessageDialog(this, "Login fails", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                new MomentFrame();
                this.dispose();
            }
        } else if (e.getActionCommand().equals("sign-up")) {
            //TODO: sign up new account
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loginButton.doClick();
        }
    }
}
