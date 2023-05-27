package priv.jeffrey.trrs.frontend.commit;

import priv.jeffrey.trrs.backend.CommitHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.Vector;

import static priv.jeffrey.trrs.frontend.commit.AddUpdatePanel.teacherExpenseTextFieldVector;
import static priv.jeffrey.trrs.frontend.commit.AddUpdatePanel.teacherIdTextFieldVector;

public class Action {
    private static String projectId;
    private static String projectName;
    private static String projectSource;
    private static int projectType;
    private static Float projectExpense;
    private static int projectStartYear;
    private static int projectEndYear;
    private static Vector<String> teacherIdList;
    private static Vector<Float> teacherExpenseList;
    private static JPanel panel;
    private static Vector<Vector<String>> queryResult;
    private static void showQueryResult() {
        if (queryResult == null) {
            JOptionPane.showMessageDialog(panel, "查询结果为空");
            return;
        }
        Object[] columns = {"项目号","教师工号","教师排名","教师承担经费"};
        DefaultTableModel model = new DefaultTableModel(null, columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        for (Vector<String> row : queryResult) {
            model.addRow(row);
        }
        JOptionPane.showMessageDialog(panel, scrollPane);
    }
    static void queryActionPerformed(JPanel queryPanel) {
        panel = queryPanel;
        try {
            projectId = DeleteQueryPanel.projectIdTextField.getText();
            if (projectId.length() > 255 || projectId.length() < 1) {
                throw new IllegalArgumentException("项目编号长度不合法");
            }
            queryResult= CommitHandler.actionQuery(projectId);
            showQueryResult();
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
            e.printStackTrace();
        }
    }

    static void deleteActionPerformed(JPanel deletePanel) {
        panel = deletePanel;
        try {
            projectId = DeleteQueryPanel.projectIdTextField.getText();
            if (projectId.length() > 255 || projectId.length() < 1) {
                throw new IllegalArgumentException("项目编号长度不合法");
            }
            CommitHandler.actionDelete(projectId);
            JOptionPane.showMessageDialog(panel, "项目承担情况删除成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
            e.printStackTrace();
        }
    }

    static void addActionPerformed(JPanel addPanel) {
        panel = addPanel;
        try {
            getProjectInformation();
            getTeacherInformation();
            CommitHandler.actionAdd(projectId, projectName, projectSource, projectType, projectExpense,
                    projectStartYear, projectEndYear, teacherIdList, teacherExpenseList);
            JOptionPane.showMessageDialog(panel, "项目承担情况添加成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
            e.printStackTrace();
        }
    }
    static void updateActionPerformed(JPanel updatePanel) {
        panel = updatePanel;
        try {
            getProjectInformation();
            getTeacherInformation();
            CommitHandler.actionUpdate(projectId, projectName, projectSource, projectType, projectExpense,
                    projectStartYear, projectEndYear, teacherIdList, teacherExpenseList);
            JOptionPane.showMessageDialog(panel, "项目承担情况更新成功");
        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getTeacherInformation() throws IllegalArgumentException {
        teacherIdList = new Vector<>();
        teacherExpenseList = new Vector<>();
        getTeacherIdList();
        getTeacherExpenseList();
        for(String x:teacherIdList){
            if(teacherIdList.indexOf(x)!=teacherIdList.lastIndexOf(x)){
                throw new IllegalArgumentException("教师工号重复");
            }
        }
    }

    private static void getTeacherIdList() throws IllegalArgumentException {
        int count = 1;
        for (JTextField x : teacherIdTextFieldVector) {
            String tmp = x.getText();
            if (tmp.length() > 5 || tmp.length() < 1) {
                throw new IllegalArgumentException("教师" + count + "工号长度不合法");
            }
            count++;
            teacherIdList.add(tmp);
        }
    }

    private static void getTeacherExpenseList() throws IllegalArgumentException {
        int count = 1;
        for (JTextField x : teacherExpenseTextFieldVector) {
            try {
                float tmp = Float.parseFloat(x.getText());
                if (tmp <= 0) {
                    throw new IllegalArgumentException("教师" + count + "经费不合法");
                }
                teacherExpenseList.add(tmp);
            } catch (NumberFormatException e_nfe) {
                throw new IllegalArgumentException("教师" + count + "经费不合法");
            }
            count++;
        }
        Float sum = 0F;
        for (Float x : teacherExpenseList) {
            sum += x;
        }
        if (!sum.equals(projectExpense)) {
            throw new IllegalArgumentException("教师经费总和与项目经费不符");
        }
    }

    private static void getProjectId() throws IllegalArgumentException {
        projectId = AddUpdatePanel.projectIdTextField.getText();
        if (projectId.length() > 255 || projectId.length() < 1) {
            throw new IllegalArgumentException("项目编号长度不合法");
        }
    }

    private static void getProjectName() throws IllegalArgumentException {
        projectName = AddUpdatePanel.projectNameTextField.getText();
        if (projectName.length() > 255 || projectName.length() < 1) {
            throw new IllegalArgumentException("项目名称长度不合法");
        }
    }

    private static void getProjectSource() throws IllegalArgumentException {
        projectSource = AddUpdatePanel.projectSourceTextField.getText();
        if (projectSource.length() > 255 || projectSource.length() < 1) {
            throw new IllegalArgumentException("项目来源长度不合法");
        }
    }

    private static void getProjectType() throws IllegalArgumentException {
        projectType = AddUpdatePanel.projectTypeTextField.getSelectedIndex();
    }

    private static void getProjectExpense() throws IllegalArgumentException {
        try {
            projectExpense = Float.parseFloat(AddUpdatePanel.totalExpenseTextField.getText());
            if (projectExpense <= 0) {
                throw new IllegalArgumentException("项目经费不合法");
            }
        } catch (NumberFormatException e_nfe) {
            throw new IllegalArgumentException("项目经费不合法");
        }
    }

    private static void getProjectStartYear() throws IllegalArgumentException {
        try {
            projectStartYear = Integer.parseInt(AddUpdatePanel.projectStartYearTextField.getText());
        } catch (NumberFormatException e_nfe) {
            throw new IllegalArgumentException("项目开始年份不合法");
        }
    }

    private static void getProjectEndYear() throws IllegalArgumentException {
        try {
            projectEndYear = Integer.parseInt(AddUpdatePanel.projectEndYearTextField.getText());
        } catch (NumberFormatException e_nfe) {
            throw new IllegalArgumentException("项目结束年份不合法");
        }
    }

    public static void getProjectInformation() throws IllegalArgumentException {

        getProjectId();
        getProjectName();
        getProjectSource();
        getProjectType();
        getProjectExpense();
        getProjectStartYear();
        getProjectEndYear();

    }
}
