/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.ui;

import main.model.AbstractAccount;
import main.model.AccountManager;
import main.model.Exceptions.AccountNullException;
import main.model.Moment;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MomentCell extends JPanel implements ActionListener {

    private static final int MAX_STRING_LENGTH = 30;
    private static final MatteBorder topBorder = BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray);
    private static final Color nameColor = new Color(28, 142, 255);

    private AccountManager accountManager;
    private Moment moment;
//    private MomentsPanel momentsPanel;
    private ImageIcon avatarImage;
    private JLabel avatarLabel;
    private JLabel nameLabel;
    private JLabel contentLabel;
    private JLabel likeListLabel;
    String[] names = {"ss", "sss", "sss"};
    private JButton likeButton;

    public MomentCell(Moment moment) {
        super();
        accountManager = AccountManager.getInstance();
//        this.momentsPanel = momentsPanel;
        this.moment = moment;
        setPreferredSize(new Dimension(MomentsPanel.WIDTH, 200));
        setBorder(topBorder);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        likeButton = new JButton("like");

        avatarLabel = new JLabel();
        File file = new File("./data/portraits/portrait_" + moment.getOwner().getUsername() + ".jpeg");
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            //
        }
        image = image.getScaledInstance(100 ,100, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        avatarLabel.setIcon(imageIcon);
        avatarLabel.setSize(100, 100);
        nameLabel = new JLabel(moment.getOwner().getNickname());
        nameLabel.setForeground(nameColor);
        contentLabel = new JLabel(moment.getContent());
        likeListLabel = new JLabel(makeNameListString(getLikeNameList(moment.getLikeList())));
        likeListLabel.setForeground(nameColor);
        likeButton.setActionCommand("like");
        likeButton.addActionListener(this);

        add(avatarLabel);
        add(nameLabel);
        add(contentLabel);
        add(likeListLabel);
        add(likeButton);
        // put constraints
        layout.putConstraint(SpringLayout.WEST, avatarLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, avatarLabel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, nameLabel, 20, SpringLayout.EAST, avatarLabel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, contentLabel, 10, SpringLayout.EAST, avatarLabel);
        layout.putConstraint(SpringLayout.NORTH, contentLabel, 5, SpringLayout.SOUTH, nameLabel);
        layout.putConstraint(SpringLayout.WEST, likeListLabel, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, likeListLabel, -15, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, likeButton, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, likeButton, -5, SpringLayout.SOUTH, this);

    }

    private List<String> getLikeNameList(List<AbstractAccount> likeList) {
        List<String> resultList = new ArrayList<>();
        for (AbstractAccount account: likeList) {
            resultList.add(account.getNickname());
        }
        return resultList;
    }

    private String makeNameListString(List<String> names) {
        String result = "♡️";
        for (String name: names) {
            if (result.length() + name.length() <= MAX_STRING_LENGTH) {
                result += name + ", ";
            }
        }
        return result.substring(0, result.length() - 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("like")) {
            try {
                accountManager.addLike(moment);
                likeListLabel.setText(makeNameListString(getLikeNameList(moment.getLikeList())));
                likeButton.setText("cancel");
                likeButton.setActionCommand("cancel");
            } catch (AccountNullException ex) {
                System.out.println("No account logged in.");
            }
        } else if (e.getActionCommand().equals("cancel")) {
            try {
                accountManager.cancelLike(moment);
                likeListLabel.setText(makeNameListString(getLikeNameList(moment.getLikeList())));
                likeButton.setText("like");
                likeButton.setActionCommand("like");
            } catch (AccountNullException ex) {
                System.out.println("No account logged in.");
            }
        }
    }
}
