package priv.jeffrey.trrs.frontend.teach;

import priv.jeffrey.trrs.backend.TeachHandler;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class Action {
    private static String courseId;
    private static String courseName;
    private static int courseTotalHours;
    private static int courseProperty;
    private static int courseYear;
    private static int courseTerm;
    private static Vector<String> teacherIdList;
    private static Vector<Integer> teacherHoursList;

    static void addActionPerformed(JPanel addPanel) {
        JPanel panel = addPanel;
        try {
            getCourseInfo();
            getTeacherInfo();
            TeachHandler.actionAdd(courseId, courseName, courseTotalHours, courseProperty, courseYear, courseTerm,
                    teacherIdList, teacherHoursList);
            JOptionPane.showMessageDialog(panel, "添加成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getTeacherInfo() throws IllegalArgumentException {
        teacherIdList = new Vector<>();
        teacherHoursList = new Vector<>();
        for (int i = 0; i < TeachPanel.teacherIdTextFieldVector.size(); i++) {
            String teacherId = TeachPanel.teacherIdTextFieldVector.get(i).getText();
            if (teacherId.length() > 255 || teacherId.length() < 1) {
                throw new IllegalArgumentException("教师工号长度不合法");
            }
            teacherIdList.add(teacherId);
            try {
                int teacherHours = Integer.parseInt(TeachPanel.teacherHoursTextFieldVector.get(i).getText());
                if (teacherHours < 0) {
                    throw new IllegalArgumentException("教师学时数不合法");
                }
                teacherHoursList.add(teacherHours);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("教师学时数不合法");
            }
        }
        int sum = 0;
        for (int x : teacherHoursList) {
            sum += x;
        }
        if (sum != courseTotalHours) {
            throw new IllegalArgumentException("教师学时数总和不等于课程总学时数");
        }
    }

    private static void getCourseInfo() throws IllegalArgumentException {
        courseId = TeachPanel.courseIdTextField.getText();
        if (courseId.length() > 255 || courseId.length() < 1) {
            throw new IllegalArgumentException("课程编号长度不合法");
        }
        courseName = TeachPanel.courseNameTextField.getText();
        if (courseName.length() > 255 || courseName.length() < 1) {
            throw new IllegalArgumentException("课程名称长度不合法");
        }
        try {
            courseTotalHours = Integer.parseInt(TeachPanel.courseTotalHoursTextField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("课程总学时数不合法");
        }
        if (courseTotalHours < 0) {
            throw new IllegalArgumentException("课程总学时数不合法");
        }
        try {
            courseProperty = Integer.parseInt(TeachPanel.coursePropertyTextField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("课程性质不合法");
        }
        try {
            courseYear = Integer.parseInt(TeachPanel.courseYearTextField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("课程年份不合法");
        }
        if (courseYear < 0) {
            throw new IllegalArgumentException("课程年份不合法");
        }
        courseTerm = TeachPanel.courseSemesterComboBox.getSelectedIndex();
    }
}
