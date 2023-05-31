package priv.jeffrey.trrs.teach;

import priv.jeffrey.trrs.backend.enums.CourseProperty;
import priv.jeffrey.trrs.backend.enums.Semester;
import priv.jeffrey.trrs.frontend.utilities.MyBox;
import priv.jeffrey.trrs.frontend.utilities.SubPanel;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.Vector;


public final class TeachPanel extends SubPanel implements ActionListener {
    static MyBox courseIdBox;
    static MyBox courseNameBox;
    static MyBox courseTotalHoursBox;
    static MyBox coursePropertyBox;
    static MyBox courseYearBox;
    static MyBox courseSemesterBox;


    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.add(new TeachPanel());
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(100, 100, 600, 600);
    }

    public TeachPanel() {
        super();
        databaseConnector =new TeachHandler();
        fieldNo2 = " 学时";
        courseIdBox = new MyBox("课程编号");
        mainBox.add(courseIdBox);
        panelInfoComponentVector.add(courseIdBox.textField);
        courseNameBox = new MyBox("课程名称");
        mainBox.add(courseNameBox);
        panelInfoComponentVector.add(courseNameBox.textField);
        courseTotalHoursBox = new MyBox("课程学时");
        mainBox.add(courseTotalHoursBox);
        panelInfoComponentVector.add(courseTotalHoursBox.textField);
        coursePropertyBox = new MyBox("课程性质", new JComboBox<>(CourseProperty.values()));
        mainBox.add(coursePropertyBox);
        panelInfoComponentVector.add(coursePropertyBox.textField);
        courseYearBox = new MyBox("开课年份");
        mainBox.add(courseYearBox);
        panelInfoComponentVector.add(courseYearBox.textField);
        courseSemesterBox = new MyBox("开课学期", new JComboBox<>(Semester.values()));
        mainBox.add(courseSemesterBox);
        panelInfoComponentVector.add(courseSemesterBox.textField);
    }

    @Override
    protected Vector<Vector<String>> getPanelInfo(boolean isAddUpdate) {
        Vector<Vector<String>> result = new Vector<>();
        Vector<String> courseInfo = new Vector<>();
        String courseId = TeachPanel.courseIdBox.textField.getText();
        if (courseId.length() > 255 || courseId.length() < 1) {
            throw new IllegalArgumentException("课程编号长度不合法");
        }
        if (!isAddUpdate) {
            courseInfo.add(courseId);
            result.add(courseInfo);
            return result;
        }
        String courseName = TeachPanel.courseNameBox.textField.getText();
        if (courseName.length() > 255 || courseName.length() < 1) {
            throw new IllegalArgumentException("课程名称长度不合法");
        }
        int courseTotalHours;
        try {
            courseTotalHours = Integer.parseInt(TeachPanel.courseTotalHoursBox.textField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("课程总学时数不合法");
        }
        if (courseTotalHours < 0) {
            throw new IllegalArgumentException("课程总学时数不合法");
        }
        int courseProperty = TeachPanel.coursePropertyBox.comboBox.getSelectedIndex();
        int courseYear;
        try {
            courseYear = Integer.parseInt(TeachPanel.courseYearBox.textField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("课程年份不合法");
        }
        if (courseYear < 0) {
            throw new IllegalArgumentException("课程年份不合法");
        }
        int courseTerm = TeachPanel.courseSemesterBox.comboBox.getSelectedIndex();

        courseInfo.add(courseId);
        courseInfo.add(courseName);
        courseInfo.add(String.valueOf(courseTotalHours));
        courseInfo.add(String.valueOf(courseProperty));
        courseInfo.add(String.valueOf(courseYear));
        courseInfo.add(String.valueOf(courseTerm));
        result.add(courseInfo);
        Vector<String> teacherIdList = new Vector<>();
        Vector<String> teacherHoursList = new Vector<>();
        int sum = 0;
        for (int i = 0; i < teacherCount - 1; i++) {
            String teacherId = teacherIdTextFieldVector.get(i).getText();
            if (teacherId.length() > 255 || teacherId.length() < 1) {
                throw new IllegalArgumentException("教师编号长度不合法");
            }
            teacherIdList.add(teacherId);
            int teacherHours;
            try {
                teacherHours = Integer.parseInt(teacherNo2TextFieldVector.get(i).getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("教师学时数不合法");
            }
            if (teacherHours < 0) {
                throw new IllegalArgumentException("教师学时数不合法");
            }
            sum += teacherHours;
            teacherHoursList.add(String.valueOf(teacherHours));
        }
        if (sum != courseTotalHours) {
            throw new IllegalArgumentException("教师学时数总和不等于课程总学时数");
        }
        result.add(teacherIdList);
        result.add(teacherHoursList);
        return result;
    }

    @Override
    protected void showSearchResult(Vector<Vector<Object>> searchResult) {
        if (searchResult.size() <= 1) {
            JOptionPane.showMessageDialog(this, "无结果");
        } else {
            TeachPanel.courseIdBox.textField.setText(searchResult.get(0).get(0).toString());
            TeachPanel.courseNameBox.textField.setText(searchResult.get(0).get(1).toString());
            TeachPanel.courseTotalHoursBox.textField.setText(searchResult.get(0).get(2).toString());
            TeachPanel.coursePropertyBox.comboBox.setSelectedIndex((int) searchResult.get(0).get(3));
            TeachPanel.courseYearBox.textField.setText(searchResult.get(0).get(4).toString());
            TeachPanel.courseSemesterBox.comboBox.setSelectedIndex((int) searchResult.get(0).get(5));
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
}
