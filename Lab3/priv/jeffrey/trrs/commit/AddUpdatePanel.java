package priv.jeffrey.trrs.commit;

import priv.jeffrey.trrs.enums.ProjectType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import static priv.jeffrey.trrs.commit.Action.addActionPerformed;
import static priv.jeffrey.trrs.commit.Action.updateActionPerformed;

public class AddUpdatePanel extends JPanel implements ActionListener {
    static Box mainBox;
    static JButton backButton;
    static JButton addButton;
    static JButton updateButton;
    static JLabel projectIdLabel;
    static JTextField projectIdTextField;
    static Box projectIdBox;
    static JLabel projectNameLabel;
    static JTextField projectNameTextField;
    static Box projectNameBox;
    static JLabel projectSourceLabel;
    static JTextField projectSourceTextField;
    static Box projectSourceBox;
    static JLabel projectTypeLabel;
    static JComboBox<ProjectType> projectTypeTextField;
    static Box projectTypeBox;
    static JLabel totalExpenseLabel;
    static JTextField totalExpenseTextField;
    static Box totalExpenseBox;
    static JLabel projectStartYearLabel;
    static JTextField projectStartYearTextField;
    static Box projectStartYearBox;
    static JLabel projectEndYearLabel;
    static JTextField projectEndYearTextField;
    static Box projectEndYearBox;
    static Vector<JTextField> teacherIdTextFieldVector;
    static Vector<JTextField> teacherExpenseTextFieldVector;
    static JButton addTeacherButton;
    static JButton deleteTeacherButton;
    static int count = 1;

    public AddUpdatePanel() {
        mainBox = Box.createVerticalBox();
        add(mainBox);
        backButton = new JButton();
        backButton.setText("返回上一页面");
        backButton.addActionListener(this);
        mainBox.add(backButton);
        addButton = new JButton();
        addButton.setText("添加");
        addButton.addActionListener(this);
        mainBox.add(addButton);
        updateButton = new JButton();
        updateButton.setText("更新");
        updateButton.addActionListener(this);
        mainBox.add(updateButton);
        addTeacherButton = new JButton();
        addTeacherButton.setText("添加教师");
        addTeacherButton.addActionListener(this);
        mainBox.add(addTeacherButton);
        deleteTeacherButton = new JButton();
        deleteTeacherButton.setText("删除教师");
        deleteTeacherButton.addActionListener(this);
        mainBox.add(deleteTeacherButton);
        teacherIdTextFieldVector = new Vector<>();
        teacherExpenseTextFieldVector = new Vector<>();
        createProjectIdComponent();
        createProjectNameComponent();
        createProjectSourceComponent();
        createProjectTypeComponent();
        createTotalExpenseComponent();
        createProjectStartYearComponent();
        createProjectEndYearComponent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            CommitPanel.cardLayout.show(CommitPanel.mainPanel, "Home");
        } else if (e.getSource().equals(addButton)) {
            addActionPerformed(this);
        }else if(e.getSource().equals(updateButton)){
            updateActionPerformed(this);
        } else if (e.getSource().equals(addTeacherButton)) {
            createTeacherComponent();
        } else if (e.getSource().equals(deleteTeacherButton)) {
            deleteTeacherComponent();
        }
    }

    private static void deleteTeacherComponent() {
        if (count == 1) {
            return;
        }
        mainBox.remove(mainBox.getComponentCount() - 1);
        teacherIdTextFieldVector.remove(teacherIdTextFieldVector.size() - 1);
        teacherExpenseTextFieldVector.remove(teacherExpenseTextFieldVector.size() - 1);
        count--;
        SwingUtilities.updateComponentTreeUI(mainBox);
    }

    private static void createTeacherComponent() {
        Box teacherBox = Box.createHorizontalBox();
        JLabel teacherIdLabel = new JLabel();
        teacherIdLabel.setText("教师" + count + "工号");
        JTextField teacherIdTextField = new JTextField();
        teacherIdTextField.setColumns(20);

        JLabel teacherExpenseLabel = new JLabel();
        teacherExpenseLabel.setText("教师" + count++ + " 承担经费");
        JTextField teacherExpenseTextField = new JTextField();
        teacherExpenseTextField.setColumns(20);

        teacherBox.add(teacherIdLabel);
        teacherBox.add(Box.createHorizontalStrut(10));
        teacherBox.add(teacherIdTextField);
        teacherBox.add(Box.createHorizontalStrut(10));
        teacherBox.add(teacherExpenseLabel);
        teacherBox.add(Box.createHorizontalStrut(10));
        teacherBox.add(teacherExpenseTextField);

        teacherIdTextFieldVector.add(teacherIdTextField);
        teacherExpenseTextFieldVector.add(teacherExpenseTextField);
        mainBox.add(teacherBox);
        SwingUtilities.updateComponentTreeUI(CommitPanel.addUpdatePanel);
    }

    public static void createProjectIdComponent() {
        projectIdLabel = new JLabel();
        projectIdLabel.setText("项目编号");
        projectIdTextField = new JTextField();
        projectIdTextField.setColumns(20);
        projectIdBox = Box.createHorizontalBox();
        projectIdBox.add(projectIdLabel);
        projectIdBox.add(Box.createHorizontalStrut(10));
        projectIdBox.add(projectIdTextField);
        mainBox.add(projectIdBox);
    }

    public static void createProjectNameComponent() {
        projectNameLabel = new JLabel();
        projectNameLabel.setText("项目名称");
        projectNameTextField = new JTextField();
        projectNameTextField.setColumns(20);
        projectNameBox = Box.createHorizontalBox();
        projectNameBox.add(projectNameLabel);
        projectNameBox.add(Box.createHorizontalStrut(10));
        projectNameBox.add(projectNameTextField);
        mainBox.add(projectNameBox);
    }

    public static void createProjectSourceComponent() {
        projectSourceLabel = new JLabel();
        projectSourceLabel.setText("项目来源");
        projectSourceTextField = new JTextField();
        projectSourceTextField.setColumns(20);
        projectSourceBox = Box.createHorizontalBox();
        projectSourceBox.add(projectSourceLabel);
        projectSourceBox.add(Box.createHorizontalStrut(10));
        projectSourceBox.add(projectSourceTextField);
        mainBox.add(projectSourceBox);
    }

    public static void createProjectTypeComponent() {
        projectTypeLabel = new JLabel();
        projectTypeLabel.setText("项目类型");
        projectTypeTextField = new JComboBox<ProjectType>(ProjectType.values());
        projectTypeTextField.setEditable(false);
        projectTypeBox = Box.createHorizontalBox();
        projectTypeBox.add(projectTypeLabel);
        projectTypeBox.add(Box.createHorizontalStrut(10));
        projectTypeBox.add(projectTypeTextField);
        mainBox.add(projectTypeBox);
    }

    public static void createTotalExpenseComponent() {
        totalExpenseLabel = new JLabel();
        totalExpenseLabel.setText("总经费");
        totalExpenseTextField = new JTextField();
        totalExpenseTextField.setColumns(20);
        totalExpenseBox = Box.createHorizontalBox();
        totalExpenseBox.add(totalExpenseLabel);
        totalExpenseBox.add(Box.createHorizontalStrut(10));
        totalExpenseBox.add(totalExpenseTextField);
        mainBox.add(totalExpenseBox);
    }

    public static void createProjectStartYearComponent() {
        projectStartYearLabel = new JLabel();
        projectStartYearLabel.setText("项目开始年份");
        projectStartYearTextField = new JTextField();
        projectStartYearTextField.setColumns(20);
        projectStartYearBox = Box.createHorizontalBox();
        projectStartYearBox.add(projectStartYearLabel);
        projectStartYearBox.add(Box.createHorizontalStrut(10));
        projectStartYearBox.add(projectStartYearTextField);
        mainBox.add(projectStartYearBox);
    }

    public static void createProjectEndYearComponent() {
        projectEndYearLabel = new JLabel();
        projectEndYearLabel.setText("项目结束年份");
        projectEndYearTextField = new JTextField();
        projectEndYearTextField.setColumns(20);
        projectEndYearBox = Box.createHorizontalBox();
        projectEndYearBox.add(projectEndYearLabel);
        projectEndYearBox.add(Box.createHorizontalStrut(10));
        projectEndYearBox.add(projectEndYearTextField);
        mainBox.add(projectEndYearBox);
    }
}
