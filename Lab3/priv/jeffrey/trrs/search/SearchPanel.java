package priv.jeffrey.trrs.search;

import priv.jeffrey.trrs.main.MainFrame;
import priv.jeffrey.trrs.utilities.MyBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SearchPanel extends JPanel implements ActionListener {
    static Box mainBox;
    static JButton homeButton;
    static MyBox teacherIdBox;
    static MyBox startYearBox;
    static MyBox endYearBox;
    static JButton searchButton;

    public SearchPanel() {
        mainBox = Box.createVerticalBox();
        add(mainBox);
        homeButton = new JButton("返回");
        homeButton.addActionListener(this);
        mainBox.add(homeButton);
        teacherIdBox = new MyBox("教师工号");
        mainBox.add(teacherIdBox);
        startYearBox = new MyBox("起始年份");
        mainBox.add(startYearBox);
        endYearBox = new MyBox("结束年份");
        mainBox.add(endYearBox);
        searchButton = new JButton("查询");
        searchButton.addActionListener(this);
        mainBox.add(searchButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("返回")) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Home");
        } else if (e.getActionCommand().equals("查询")) {
            String teacherId = teacherIdBox.textField.getText();
            if (teacherId.length() > 5 || teacherId.length() < 1) {
                JOptionPane.showMessageDialog(null, "教师工号长度不合法。", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int startYear;
            int endYear;
            try {
                startYear = Integer.parseInt(startYearBox.textField.getText());
                endYear = Integer.parseInt(endYearBox.textField.getText());
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "年份格式不合法。", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startYear > endYear) {
                JOptionPane.showMessageDialog(null, "起始年份不能大于结束年份。", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startYear < 1900 || endYear > 2100) {
                JOptionPane.showMessageDialog(null, "年份不合法。", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                SearchHandler.action(teacherId, startYear, endYear);
                JOptionPane.showMessageDialog(null, "查询成功，结果已输出到文件。", "成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}