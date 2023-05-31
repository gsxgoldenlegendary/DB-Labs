package priv.jeffrey.trrs.main;

import priv.jeffrey.trrs.commit.CommitPanel;
import priv.jeffrey.trrs.publish.PublishPanel;
import priv.jeffrey.trrs.search.SearchPanel;
import priv.jeffrey.trrs.teach.TeachPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public static JPanel mainPanel;
    public static HomePanel homePanel;
    public static PublishPanel publishPanel;
    public static CommitPanel commitPanel;
    public static TeachPanel teachPanel;
    public static SearchPanel searchPanel;
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
        searchPanel = new SearchPanel();
        mainPanel.add(searchPanel, "Search");

        cardLayout.show(mainPanel, "Home");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class HomePanel extends JPanel implements ActionListener {
    static JButton publishButton;
    static JButton commitButton;
    static JButton teachButton;
    static JButton queryButton;
    Box box;

    public HomePanel() {
        box = Box.createVerticalBox();
        add(box);

        publishButton = new JButton();
        publishButton.setText("登记科研成果情况");
        commitButton = new JButton("登记承担项目情况");
        teachButton = new JButton("登记主讲课程情况");
        queryButton = new JButton("查询统计");

        box.add(publishButton);
        box.add(Box.createVerticalStrut(100));
        box.add(commitButton);
        box.add(Box.createVerticalStrut(100));
        box.add(teachButton);
        box.add(Box.createVerticalStrut(100));
        box.add(queryButton);

        publishButton.addActionListener(this);
        commitButton.addActionListener(this);
        teachButton.addActionListener(this);
        queryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(publishButton)) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Publish");
        } else if (e.getSource().equals(commitButton)) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Commit");
        } else if (e.getSource().equals(teachButton)) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Teach");
        } else if (e.getSource().equals(queryButton)) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Search");
        }
    }
}
