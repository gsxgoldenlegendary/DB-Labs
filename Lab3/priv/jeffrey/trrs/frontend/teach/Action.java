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
    private static JPanel panel;

    private static void showSearchResult(Vector<Vector<String>> searchResult) {
        if (searchResult.size() <= 1) {
            JOptionPane.showMessageDialog(panel, "未找到相关课程");
            return;
        }
        TeachPanel.courseIdTextField.setText(searchResult.get(0).get(0));
        TeachPanel.courseNameTextField.setText(searchResult.get(0).get(1));
        TeachPanel.courseTotalHoursTextField.setText(searchResult.get(0).get(2));
        TeachPanel.coursePropertyTextField.setText(searchResult.get(0).get(3));
        while (TeachPanel.count != 1) {
            TeachPanel.deleteTeacherComponent();
        }
        for (int i = 1; i < searchResult.size(); i++) {
            TeachPanel.createTeacherComponent();
            TeachPanel.teacherIdTextFieldVector.get(i - 1).setText(searchResult.get(i).get(0));
            TeachPanel.teacherHoursTextFieldVector.get(i - 1).setText(searchResult.get(i).get(1));
            TeachPanel.courseYearTextField.setText(searchResult.get(i).get(2));
            TeachPanel.courseSemesterComboBox.setSelectedIndex(Integer.parseInt(searchResult.get(i).get(3)) );
        }

    }

    static void searchActionPerformed(JPanel searchPanel) {
        panel = searchPanel;
        try {
            courseId = TeachPanel.courseIdTextField.getText();
            if (courseId.length() > 255 || courseId.length() < 1) {
                throw new IllegalArgumentException("课程编号长度不合法");
            }
            showSearchResult(TeachHandler.actionSearch(courseId));
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(searchPanel, e.getMessage());
            e.printStackTrace();
        }
    }

    static void deleteActionPerformed(JPanel deletePanel) {
        try {
            courseId = TeachPanel.courseIdTextField.getText();
            if (courseId.length() > 255 || courseId.length() < 1) {
                throw new IllegalArgumentException("课程编号长度不合法");
            }
            TeachHandler.actionDelete(courseId);
            JOptionPane.showMessageDialog(deletePanel, "删除成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(deletePanel, e.getMessage());
            e.printStackTrace();
        }
    }

    static void updateActionPerformed(JPanel updatePanel) {
        try {
            getCourseInfo();
            getTeacherInfo();
            TeachHandler.actionUpdate(courseId, courseName, courseTotalHours, courseProperty, courseYear, courseTerm,
                    teacherIdList, teacherHoursList);
            JOptionPane.showMessageDialog(updatePanel, "修改成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(updatePanel, e.getMessage());
            e.printStackTrace();
        }
    }

    static void addActionPerformed(JPanel addPanel) {
        try {
            getCourseInfo();
            getTeacherInfo();
            TeachHandler.actionAdd(courseId, courseName, courseTotalHours, courseProperty, courseYear, courseTerm,
                    teacherIdList, teacherHoursList);
            JOptionPane.showMessageDialog(addPanel, "添加成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(addPanel, e.getMessage());
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
