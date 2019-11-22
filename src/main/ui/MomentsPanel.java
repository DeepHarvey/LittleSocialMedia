/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.ui;

import main.model.AccountManager;
import main.model.Exceptions.AccountNullException;
import main.model.Moment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MomentsPanel extends JPanel {

    public static final int WIDTH = 380;

    private AccountManager accountManager;
    private java.util.List<Moment> momentList;
    private java.util.List<MomentCell> cells;


    public MomentsPanel() {
        super();
        accountManager = AccountManager.getInstance();
        momentList = new ArrayList<>();
        cells = new ArrayList<>();
        try {
            accountManager = AccountManager.getInstance();
            momentList.addAll(accountManager.getMoments());
            momentList.addAll(accountManager.getFriendMoments());
        } catch (AccountNullException e) {
            System.out.println("No account logged in");
        }
        setPreferredSize(new Dimension(WIDTH, 600));
        setLayout(new GridLayout(0,1,0,0));
        for (Moment moment: momentList) {
            cells.add(new MomentCell(moment));
        }
        for (MomentCell cell: cells) {
            add(cell);
        }
        setPreferredSize(new Dimension(WIDTH, 200 * cells.size()));
    }

    public void update() {
        removeAll();
        cells = new ArrayList<>();
        momentList = new ArrayList<>();
        try {
            momentList.addAll(accountManager.getMoments());
            momentList.addAll(accountManager.getFriendMoments());
        } catch (AccountNullException e) {
            System.out.println("No account logged in");
        }
        for (Moment moment: momentList) {
            cells.add(new MomentCell(moment));
        }
        for (MomentCell cell: cells) {
            add(cell);
        }
        setPreferredSize(new Dimension(WIDTH, 200 * cells.size()));
        updateUI();
    }
}
