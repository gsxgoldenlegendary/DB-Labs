package priv.jeffrey.trrs.frontend.utilities;

import priv.jeffrey.trrs.backend.DBConnector;
import priv.jeffrey.trrs.frontend.home.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public abstract class SubPanel extends JPanel implements ActionListener, Action {
    static Box mainBox;
    static Box topMenuBox;
    static Box bottomMenuBox;
    static int teacherCount = 1;
    static String fieldNo2 = "";
    static Vector<JTextField> teacherIdTextFieldVector;
    static Vector<JTextField> teacherNo2TextFieldVector;
    static Vector<JTextField> panelInfoTextFieldVector;

    public SubPanel() {
        super();
        mainBox = Box.createVerticalBox();
        topMenuBox = Box.createHorizontalBox();
        bottomMenuBox = Box.createHorizontalBox();
        teacherIdTextFieldVector = new Vector<>();
        teacherNo2TextFieldVector = new Vector<>();

        add(mainBox);

        mainBox.add(topMenuBox, 0);

        createMenuButton("返回", true);
        createMenuButton("添加", true);
        createMenuButton("更新", true);
        createMenuButton("删除", true);
        createMenuButton("查询", true);


        mainBox.add(bottomMenuBox, -1);
        createMenuButton("添加教师", false);
        createMenuButton("删除教师", false);
    }

    private void createMenuButton(String text, boolean isTopMenu) {
        JButton button = new JButton();
        button.setText(text);
        button.addActionListener(this);
        if (isTopMenu) {
            topMenuBox.add(button);
            topMenuBox.add(Box.createHorizontalStrut(10));
        } else {
            bottomMenuBox.add(button);
            bottomMenuBox.add(Box.createHorizontalStrut(10));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("返回")) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Home");
        } else if (e.getActionCommand().equals("添加教师")) {
            createTeacherComponent(fieldNo2);
        } else if (e.getActionCommand().equals("删除教师")) {
            deleteTeacherComponent();
        }
    }

    static void createTeacherComponent(String blankNo2) {
        MyBox teacherIdBox = new MyBox("教师 " + teacherCount + " 工号");
        MyBox teacherNo2Box = new MyBox("教师 " + teacherCount + blankNo2);
        teacherCount++;
        teacherIdTextFieldVector.add(teacherIdBox.textField);
        teacherNo2TextFieldVector.add(teacherNo2Box.textField);
        mainBox.add(teacherIdBox, -1);
        mainBox.add(teacherNo2Box, -1);
        SwingUtilities.updateComponentTreeUI(mainBox);
    }

    static void deleteTeacherComponent() {
        if (teacherCount <= 1) {
            return;
        }
        mainBox.remove(mainBox.getComponentCount() - 1);
        mainBox.remove(mainBox.getComponentCount() - 1);
        teacherIdTextFieldVector.remove(teacherIdTextFieldVector.size() - 1);
        teacherNo2TextFieldVector.remove(teacherNo2TextFieldVector.size() - 1);
        teacherCount--;
        SwingUtilities.updateComponentTreeUI(mainBox);
    }

    abstract Vector<Vector<Object>> getPanelInfo();

    void showSearchResult(Vector<Vector<Object>> searchResult) {
        if (searchResult.size() <= 1) {
            JOptionPane.showMessageDialog(this, "无结果");
        } else {
            for (int i = 0; i < searchResult.get(0).size(); i++) {
                panelInfoTextFieldVector.get(i).setText(searchResult.get(0).get(i).toString());
            }
            while (teacherCount > 1) {
                deleteTeacherComponent();
            }
            for (int i = 1; i < searchResult.size(); i++) {
                createTeacherComponent(fieldNo2);
                teacherIdTextFieldVector.get(i - 1).setText(searchResult.get(i).get(0).toString());
                teacherNo2TextFieldVector.get(i - 1).setText(searchResult.get(i).get(1).toString());
            }
            JOptionPane.showMessageDialog(this, "查询成功!");
        }
    }

    @Override
    public void addActionPerformed(JPanel panel, DBConnector connector) {
        try {
            connector.add(getPanelInfo());
            JOptionPane.showMessageDialog(panel, "添加成功!");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateActionPerformed(JPanel panel, DBConnector connector) {
        try {
            connector.update(getPanelInfo());
            JOptionPane.showMessageDialog(panel, "修改成功!");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteActionPerformed(JPanel panel, DBConnector connector) {
        try {
            connector.delete(getPanelInfo());
            JOptionPane.showMessageDialog(panel, "删除成功!");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void searchActionPerformed(JPanel panel, DBConnector connector) {
        try {
            showSearchResult(connector.search(getPanelInfo()));
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
        }
    }
}
