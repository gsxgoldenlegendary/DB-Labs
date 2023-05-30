package priv.jeffrey.trrs.frontend.home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel implements ActionListener {
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
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Query");
        }
    }
}
