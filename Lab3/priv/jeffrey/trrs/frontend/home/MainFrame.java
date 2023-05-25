package priv.jeffrey.trrs.frontend.home;

import priv.jeffrey.trrs.frontend.publish.PublishPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame{
    public static JPanel mainPanel;
    public static HomePanel homePanel;
    public static PublishPanel publishPanel;
    public static CardLayout cardLayout;
    public MainFrame() {

        setTitle("教学科研登记系统");
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);
        add(mainPanel, BorderLayout.CENTER);
        homePanel = new HomePanel();
        publishPanel = new PublishPanel();
        mainPanel.add(homePanel, "Home");
        mainPanel.add(publishPanel, "Publish");
        cardLayout.show(mainPanel, "Home");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
