package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.frontend.home.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel implements ActionListener {
    public final JButton homeButton;
    public final JButton addButton;
    public final JButton deleteButton;
    public final JButton updateButton;
    public final JButton queryButton;

    public HomePanel(){
        homeButton = new JButton();
        homeButton.setText("返回主页面");
        addButton = new JButton();
        addButton.setText("增加发表论文情况");
        deleteButton = new JButton();
        deleteButton.setText("删除发表论文情况");
        updateButton = new JButton();
        updateButton.setText("更改发表论文情况");
        queryButton = new JButton();
        queryButton.setText("查询发表论文情况");

        add(homeButton);
        add(addButton);
        add(deleteButton);
        add(updateButton);
        add(queryButton);

        homeButton.addActionListener(this);
        addButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(homeButton)){
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Home");
        }else if(e.getSource().equals(addButton)){
            PublishPanel.cardLayout.show(PublishPanel.mainPanel, "Add");
        }
    }
}
