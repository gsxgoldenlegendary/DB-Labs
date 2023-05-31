package priv.jeffrey.trrs.frontend.home;

import priv.jeffrey.trrs.frontend.commit.CommitPanel;
import priv.jeffrey.trrs.frontend.publish.PublishPanel;
import priv.jeffrey.trrs.teach.TeachPanel;

import javax.swing.*;

import java.awt.*;

public class MainFrame extends JFrame {
    public static JPanel mainPanel;
    public static HomePanel homePanel;
    public static PublishPanel publishPanel;
    public static CommitPanel commitPanel;
    public static TeachPanel teachPanel;

    public static CardLayout cardLayout;

    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        setTitle("教学科研登记系统");
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        homePanel = new HomePanel();
        mainPanel.add(homePanel, "Home");
        publishPanel = new PublishPanel();
        mainPanel.add(publishPanel, "Publish");
        commitPanel = new CommitPanel();
        mainPanel.add(commitPanel, "Commit");
        teachPanel = new TeachPanel();
        mainPanel.add(teachPanel, "Teach");

        cardLayout.show(mainPanel, "Home");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
