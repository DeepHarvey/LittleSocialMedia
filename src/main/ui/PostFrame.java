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
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostFrame extends JFrame implements ActionListener {
    private static final Font labelFont = new Font ("Georgia", Font.BOLD, 14);
    private static final MatteBorder areaBorder = BorderFactory.createMatteBorder(1,1,1,1,Color.white);

    private MomentFrame parentFrame;
    private JTextArea contentArea;
    private JPanel contentPanel;
    private AccountManager accountManager = AccountManager.getInstance();
    private JButton publishButton;

    public PostFrame(MomentFrame parentFrame) {
        super("Post ");
        this.parentFrame = parentFrame;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));
        contentPanel = (JPanel) getContentPane();
        contentPanel.setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout(0,1,1,1));
        setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(15, 50, 15, 50));

        JLabel label = new JLabel("Post your moment", JLabel.CENTER);
        label.setSize(100, 30);
        label.setFont(new Font("Georgia", Font.BOLD, 28));
//        JLabel contentLabel = new JLabel("content:", JLabel.CENTER);
        label.setFont(labelFont);
        label.setForeground(Color.getHSBColor(0, 100, 0 ));

        JLabel blankLabel = new JLabel("", JLabel.CENTER);
        label.setFont(labelFont);
        label.setForeground(Color.getHSBColor(0, 100, 0 ));
        publishButton = new JButton("publish");
        publishButton.setActionCommand("publish");
        publishButton.addActionListener(this);

        contentArea = new JTextArea();
        contentArea.setOpaque(false);
        contentArea.setBorder(areaBorder);

        contentPanel.add(label, BorderLayout.PAGE_START);
        contentPanel.add(contentArea, BorderLayout.CENTER);
        contentPanel.add(blankLabel, BorderLayout.LINE_END);
        contentPanel.add(publishButton, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("publish")) {
            String content = contentArea.getText();
            try {
                accountManager.publishMoment(content);
                JOptionPane.showMessageDialog(this, "Posted successfully!", "lol", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                parentFrame.update();
            } catch (AccountNullException ex) {
                System.out.println("No account logged in.");
            }
        }
    }
}
