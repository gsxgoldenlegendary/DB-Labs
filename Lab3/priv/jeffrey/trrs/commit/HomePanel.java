package priv.jeffrey.trrs.commit;

import priv.jeffrey.trrs.main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel implements ActionListener {
    static JButton homeButton;
    static JButton addUpdateButton;
    static JButton deleteQueryButton;

    public HomePanel() {
        homeButton = new JButton();
        homeButton.setText("返回主页面");
        addUpdateButton = new JButton();
        addUpdateButton.setText("增加与修改承担项目情况");
        deleteQueryButton = new JButton();
        deleteQueryButton.setText("删除与查询项目承担情况");

        add(homeButton);
        add(addUpdateButton);
        add(deleteQueryButton);

        homeButton.addActionListener(this);
        addUpdateButton.addActionListener(this);
        deleteQueryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(homeButton)) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Home");
        } else if (e.getSource().equals(addUpdateButton)) {
            CommitPanel.cardLayout.show(CommitPanel.mainPanel, "AddUpdate");
        } else if (e.getSource().equals(deleteQueryButton)) {
            CommitPanel.cardLayout.show(CommitPanel.mainPanel, "DeleteQuery");
        }
    }
}
