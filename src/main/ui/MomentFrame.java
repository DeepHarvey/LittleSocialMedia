/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.ui;

import main.model.AbstractAccount;
import main.model.AccountManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MomentFrame extends JFrame {

    private NavigationPanel navigationPanel;
    private MomentsPanel momentsPanel;
    private JPanel contentPanel;

    public MomentFrame() {
        super("Moments");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 700));
        contentPanel = ((JPanel) getContentPane());
        contentPanel.setBorder(new EmptyBorder(5, 0, 5, 0) );
        navigationPanel = new NavigationPanel(this);
        momentsPanel = new MomentsPanel();
        JScrollPane scrollMomentsPane = new JScrollPane(momentsPanel);
        add(scrollMomentsPane, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void update() {
        momentsPanel.update();
        //navigationPanel.update();
    }

}
