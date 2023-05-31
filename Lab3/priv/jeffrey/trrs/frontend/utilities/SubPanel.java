package priv.jeffrey.trrs.frontend.utilities;

import priv.jeffrey.trrs.backend.DatabaseConnector;
import priv.jeffrey.trrs.frontend.home.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;

public abstract class SubPanel extends JPanel implements ActionListener {
    protected static Box mainBox;
    static Box topMenuBox;
    static Box bottomMenuBox;
    protected static int teacherCount = 1;
    protected static String fieldNo2 = "";
    protected static Vector<JTextField> teacherIdTextFieldVector;
    protected static Vector<JTextField> teacherNo2TextFieldVector;
    protected static Vector<JTextField> panelInfoComponentVector;
    protected static DatabaseConnector databaseConnector;

    public SubPanel() {
        super();
        mainBox = Box.createVerticalBox();
        topMenuBox = Box.createHorizontalBox();
        bottomMenuBox = Box.createHorizontalBox();
        teacherIdTextFieldVector = new Vector<>();
        teacherNo2TextFieldVector = new Vector<>();
        panelInfoComponentVector = new Vector<>();

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
        } else {
            try {
                if (e.getActionCommand().equals("添加")) {
                    databaseConnector.insert(getPanelInfo(true));
                    JOptionPane.showMessageDialog(this, "添加成功!");
                } else if (e.getActionCommand().equals("更新")) {
                    databaseConnector.update(getPanelInfo(true));
                    JOptionPane.showMessageDialog(this, "更新成功!");
                } else if (e.getActionCommand().equals("删除")) {
                    databaseConnector.delete(getPanelInfo(false));
                    JOptionPane.showMessageDialog(this, "删除成功!");
                } else if (e.getActionCommand().equals("查询")) {
                    showSearchResult(Objects.requireNonNull(databaseConnector.search(getPanelInfo(false))));
                }
            } catch (SQLException|IllegalArgumentException e_sql) {
                JOptionPane.showMessageDialog(this, e_sql.getMessage());
                e_sql.printStackTrace();
            }
        }
    }
    protected void createTeacherComponent(String blankNo2) {
        MyBox teacherIdBox = new MyBox("教师 " + teacherCount + " 工号");
        MyBox teacherNo2Box = new MyBox("教师 " + teacherCount + blankNo2);
        teacherCount++;
        teacherIdTextFieldVector.add(teacherIdBox.textField);
        teacherNo2TextFieldVector.add(teacherNo2Box.textField);
        mainBox.add(teacherIdBox, -1);
        mainBox.add(teacherNo2Box, -1);
        SwingUtilities.updateComponentTreeUI(mainBox);
    }
    protected void deleteTeacherComponent() {
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
    abstract protected Vector<Vector<String>> getPanelInfo(boolean isAddUpdate);
    abstract protected void showSearchResult(Vector<Vector<Object>> searchResult);

}
