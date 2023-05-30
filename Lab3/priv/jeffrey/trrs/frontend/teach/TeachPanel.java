package priv.jeffrey.trrs.frontend.teach;

import priv.jeffrey.trrs.backend.enums.Semester;
import priv.jeffrey.trrs.frontend.home.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import static priv.jeffrey.trrs.frontend.teach.Action.*;

public final class TeachPanel extends JPanel implements ActionListener {
    static Box mainBox;
    static JButton backButton;
    static JButton addButton;
    static JButton updateButton;
    static JButton deleteButton;
    static JButton searchButton;
    static JLabel courseIdLabel;
    static JTextField courseIdTextField;
    static Box courseIdBox;
    static JLabel courseNameLabel;
    static JTextField courseNameTextField;
    static Box courseNameBox;
    static JLabel courseTotalHoursLabel;
    static JTextField courseTotalHoursTextField;
    static Box courseTotalHoursBox;
    static JLabel coursePropertyLabel;
    static JTextField coursePropertyTextField;
    static Box coursePropertyBox;
    static JLabel courseYearLabel;
    static JTextField courseYearTextField;
    static Box courseYearBox;
    static JLabel courseSemesterLabel;
    static JComboBox<Semester> courseSemesterComboBox;
    static Box courseSemesterBox;
    static Vector<JTextField> teacherIdTextFieldVector;
    static Vector<JTextField> teacherHoursTextFieldVector;
    static JButton addTeacherButton;
    static JButton deleteTeacherButton;
    static int count = 1;

    public TeachPanel() {
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
        deleteButton = new JButton();
        deleteButton.setText("删除");
        deleteButton.addActionListener(this);
        mainBox.add(deleteButton);
        searchButton = new JButton();
        searchButton.setText("查询");
        searchButton.addActionListener(this);
        mainBox.add(searchButton);
        addTeacherButton = new JButton();
        addTeacherButton.setText("添加教师");
        addTeacherButton.addActionListener(this);
        mainBox.add(addTeacherButton);
        deleteTeacherButton = new JButton();
        deleteTeacherButton.setText("删除教师");
        deleteTeacherButton.addActionListener(this);
        mainBox.add(deleteTeacherButton);

        courseIdLabel = new JLabel();
        courseIdLabel.setText("课程编号");
        courseIdTextField = new JTextField();
        courseIdTextField.setColumns(10);
        courseIdBox = Box.createHorizontalBox();
        courseIdBox.add(courseIdLabel);
        courseIdBox.add(courseIdTextField);
        mainBox.add(courseIdBox);
        courseNameLabel = new JLabel();
        courseNameLabel.setText("课程名称");
        courseNameTextField = new JTextField();
        courseNameTextField.setColumns(10);
        courseNameBox = Box.createHorizontalBox();
        courseNameBox.add(courseNameLabel);
        courseNameBox.add(courseNameTextField);
        mainBox.add(courseNameBox);
        courseTotalHoursLabel = new JLabel();
        courseTotalHoursLabel.setText("课程总学时");
        courseTotalHoursTextField = new JTextField();
        courseTotalHoursTextField.setColumns(10);
        courseTotalHoursBox = Box.createHorizontalBox();
        courseTotalHoursBox.add(courseTotalHoursLabel);
        courseTotalHoursBox.add(courseTotalHoursTextField);
        mainBox.add(courseTotalHoursBox);
        coursePropertyLabel = new JLabel();
        coursePropertyLabel.setText("课程性质");
        coursePropertyTextField = new JTextField();
        coursePropertyTextField.setColumns(10);
        coursePropertyBox = Box.createHorizontalBox();
        coursePropertyBox.add(coursePropertyLabel);
        coursePropertyBox.add(coursePropertyTextField);
        mainBox.add(coursePropertyBox);
        courseYearLabel = new JLabel();
        courseYearLabel.setText("开课年份");
        courseYearTextField = new JTextField();
        courseYearTextField.setColumns(10);
        courseYearBox = Box.createHorizontalBox();
        courseYearBox.add(courseYearLabel);
        courseYearBox.add(courseYearTextField);
        mainBox.add(courseYearBox);
        courseSemesterLabel = new JLabel();
        courseSemesterLabel.setText("开课学期");
        courseSemesterComboBox = new JComboBox<>();
        courseSemesterComboBox.setModel(new DefaultComboBoxModel<>(Semester.values()));
        courseSemesterBox = Box.createHorizontalBox();
        courseSemesterBox.add(courseSemesterLabel);
        courseSemesterBox.add(courseSemesterComboBox);
        mainBox.add(courseSemesterBox);
        teacherIdTextFieldVector = new Vector<>();
        teacherHoursTextFieldVector = new Vector<>();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backButton)) {
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Home");
        } else if (e.getSource().equals(addTeacherButton)) {
            createTeacherComponent();
        } else if (e.getSource().equals(deleteTeacherButton)) {
            deleteTeacherComponent();
        } else if (e.getSource().equals(addButton)) {
            addActionPerformed(this);
        } else if (e.getSource().equals(updateButton)) {
            updateActionPerformed(this);
        } else if (e.getSource().equals(deleteButton)) {
            deleteActionPerformed(this);
        } else if (e.getSource().equals(searchButton)) {
            searchActionPerformed(this);
        }
    }

    static void deleteTeacherComponent() {
        if (count == 1) {
            return;
        }
        mainBox.remove(mainBox.getComponentCount() - 1);
        teacherIdTextFieldVector.remove(teacherIdTextFieldVector.size() - 1);
        teacherHoursTextFieldVector.remove(teacherHoursTextFieldVector.size() - 1);
        count--;
        SwingUtilities.updateComponentTreeUI(mainBox);
    }

    static void createTeacherComponent() {
        Box teacherBox = Box.createHorizontalBox();
        JLabel teacherIdLabel = new JLabel();
        teacherIdLabel.setText("教师" + count + "工号");
        JTextField teacherIdTextField = new JTextField();
        teacherIdTextField.setColumns(20);

        JLabel teacherHoursLabel = new JLabel();
        teacherHoursLabel.setText("教师" + count++ + " 承担学时");
        JTextField teacherHoursTextField = new JTextField();
        teacherHoursTextField.setColumns(20);

        teacherBox.add(teacherIdLabel);
        teacherBox.add(Box.createHorizontalStrut(10));
        teacherBox.add(teacherIdTextField);
        teacherBox.add(Box.createHorizontalStrut(10));
        teacherBox.add(teacherHoursLabel);
        teacherBox.add(Box.createHorizontalStrut(10));
        teacherBox.add(teacherHoursTextField);

        teacherIdTextFieldVector.add(teacherIdTextField);
        teacherHoursTextFieldVector.add(teacherHoursTextField);
        mainBox.add(teacherBox);
        SwingUtilities.updateComponentTreeUI(mainBox);
    }
}
