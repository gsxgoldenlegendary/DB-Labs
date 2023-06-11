package priv.jeffrey.trrs.commit;

import priv.jeffrey.trrs.enums.ProjectType;
import priv.jeffrey.trrs.utilities.MyBox;
import priv.jeffrey.trrs.utilities.SubPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CommitPanel extends SubPanel implements ActionListener {
    static MyBox projectIdBox;
    static MyBox projectNameBox;
    static MyBox projectSourceBox;
    static MyBox projectTypeBox;
    static MyBox totalExpenseBox;
    static MyBox projectStartYearBox;
    static MyBox projectEndYearBox;

    public CommitPanel() {
        super(new CommitHandler(), " 经费");
        projectIdBox = new MyBox("项目编号");
        mainBox.add(projectIdBox);
        panelInfoComponentVector.add(projectIdBox.textField);
        projectNameBox = new MyBox("项目名称");
        mainBox.add(projectNameBox);
        panelInfoComponentVector.add(projectNameBox.textField);
        projectSourceBox = new MyBox("项目来源");
        mainBox.add(projectSourceBox);
        panelInfoComponentVector.add(projectSourceBox.textField);
        projectTypeBox = new MyBox("项目类型", new JComboBox<>(ProjectType.values()));
        mainBox.add(projectTypeBox);
        panelInfoComponentVector.add(projectTypeBox.textField);
        totalExpenseBox = new MyBox("项目经费");
        mainBox.add(totalExpenseBox);
        panelInfoComponentVector.add(totalExpenseBox.textField);
        projectStartYearBox = new MyBox("开始年份");
        mainBox.add(projectStartYearBox);
        panelInfoComponentVector.add(projectStartYearBox.textField);
        projectEndYearBox = new MyBox("结束年份");
        mainBox.add(projectEndYearBox);
        panelInfoComponentVector.add(projectEndYearBox.textField);
    }

    @Override
    protected Vector<Vector<String>> getPanelInfo(boolean isAddUpdate) {
        Vector<Vector<String>> result = new Vector<>();
        Vector<String> projectInfo = new Vector<>();
        String projectId = projectIdBox.textField.getText().strip();
        if (projectId.length() > 255 || projectId.length() < 1) {
            throw new IllegalArgumentException("项目编号长度不合法");
        }
        if (!isAddUpdate) {
            projectInfo.add(projectId);
            result.add(projectInfo);
            return result;
        }
        String projectName = projectNameBox.textField.getText();
        if (projectName.length() > 255 || projectName.length() < 1) {
            throw new IllegalArgumentException("项目名称长度不合法");
        }
        String projectSource = projectSourceBox.textField.getText();
        if (projectSource.length() > 255 || projectSource.length() < 1) {
            throw new IllegalArgumentException("项目来源长度不合法");
        }
        int projectType = projectTypeBox.comboBox.getSelectedIndex();
        float totalExpense;
        try {
            totalExpense = Float.parseFloat(totalExpenseBox.textField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("项目经费格式不合法");
        }
        if (totalExpense <= 0) {
            throw new IllegalArgumentException("项目经费格式不合法");
        }
        int projectStartYear;
        try {
            projectStartYear = Integer.parseInt(projectStartYearBox.textField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("开始年份格式不合法");
        }
        if (projectStartYear < 0 || projectStartYear > 9999) {
            throw new IllegalArgumentException("开始年份格式不合法");
        }
        int projectEndYear;
        try {
            projectEndYear = Integer.parseInt(projectEndYearBox.textField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("结束年份格式不合法");
        }
        if (projectEndYear < 0 || projectEndYear > 9999) {
            throw new IllegalArgumentException("结束年份格式不合法");
        }
        if (projectStartYear > projectEndYear) {
            throw new IllegalArgumentException("开始年份不能大于结束年份");
        }
        projectInfo.add(projectId);
        projectInfo.add(projectName);
        projectInfo.add(projectSource);
        projectInfo.add(String.valueOf(projectType));
        projectInfo.add(String.valueOf(totalExpense));
        projectInfo.add(String.valueOf(projectStartYear));
        projectInfo.add(String.valueOf(projectEndYear));
        result.add(projectInfo);
        Vector<String> teacherIdList = new Vector<>();
        Vector<String> teacherExpenseList = new Vector<>();
        float sum = 0F;
        for (int i = 0; i < teacherCount - 1; i++) {
            String teacherId = teacherIdTextFieldVector.get(i).getText();
            if (teacherId.length() > 5 || teacherId.length() < 1) {
                throw new IllegalArgumentException("教师工号长度不合法");
            }
            teacherIdList.add(teacherId);
            float teacherExpense;
            try {
                teacherExpense = Float.parseFloat(teacherNo2TextFieldVector.get(i).getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("教师经费不合法");
            }
            if (teacherExpense <= 0) {
                throw new IllegalArgumentException("教师经费不合法");
            }
            sum += teacherExpense;
            teacherExpenseList.add(String.valueOf(teacherExpense));
        }
        if (sum != totalExpense) {
            throw new IllegalArgumentException("教师承担经费总和不等于项目经费");
        }
        result.add(teacherIdList);
        result.add(teacherExpenseList);
        return result;
    }

    @Override
    protected void showSearchResult(Vector<Vector<Object>> searchResult) {
        if (searchResult.size() <= 1) {
            JOptionPane.showMessageDialog(this, "未找到符合条件的记录。", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            CommitPanel.projectIdBox.textField.setText(searchResult.get(0).get(0).toString());
            CommitPanel.projectNameBox.textField.setText(searchResult.get(0).get(1).toString());
            CommitPanel.projectSourceBox.textField.setText(searchResult.get(0).get(2).toString());
            CommitPanel.projectTypeBox.comboBox.setSelectedIndex(Integer.parseInt(searchResult.get(0).get(3).toString()));
            CommitPanel.totalExpenseBox.textField.setText(searchResult.get(0).get(4).toString());
            CommitPanel.projectStartYearBox.textField.setText(searchResult.get(0).get(5).toString());
            CommitPanel.projectEndYearBox.textField.setText(searchResult.get(0).get(6).toString());
            while (teacherCount > 1) {
                deleteTeacherComponent();
            }
            for (int i = 1; i < searchResult.size(); i++) {
                createTeacherComponent(fieldNo2);
                teacherIdTextFieldVector.get(i - 1).setText(searchResult.get(i).get(0).toString());
                teacherNo2TextFieldVector.get(i - 1).setText(searchResult.get(i).get(1).toString());
            }
            JOptionPane.showMessageDialog(this, "查询成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
