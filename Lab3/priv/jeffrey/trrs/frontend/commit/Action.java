package priv.jeffrey.trrs.frontend.commit;

import priv.jeffrey.trrs.backend.CommitHandler;

import javax.swing.*;
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

    static void addActionPerformed(JPanel addPanel){
        panel = addPanel;
        getProjectInformation();
        getTeacherInformation();
        try {
            CommitHandler.actionAdd(projectId, projectName, projectSource, projectType, projectExpense, projectStartYear, projectEndYear, teacherIdList, teacherExpenseList);
            JOptionPane.showMessageDialog(panel, "项目承担情况添加成功");
        }catch (SQLException e_sql){
            JOptionPane.showMessageDialog(panel, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }

    private static void getTeacherInformation(){
        teacherIdList = new Vector<>();
        teacherExpenseList = new Vector<>();
        try {
            getTeacherIdList();
            getTeacherExpenseList();
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(panel, e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getTeacherIdList()throws IllegalArgumentException{
        int count = 1;
        for(JTextField x:teacherIdTextFieldVector){
            String tmp= x.getText();
            if(tmp.length()>5||tmp.length()<1){
                throw new IllegalArgumentException("教师"+count+"工号长度不合法");
            }
            count++;
            teacherIdList.add(tmp);
        }
    }

    private static void getTeacherExpenseList()throws IllegalArgumentException{
        int count = 1;
        for(JTextField x:teacherExpenseTextFieldVector){
            try{
                teacherExpenseList.add(Float.parseFloat(x.getText()));
            }catch (NumberFormatException e_nfe){
                throw new IllegalArgumentException("教师"+count+"经费不合法");
            }
            count++;
        }
        Float sum= 0F;
        for(Float x:teacherExpenseList){
            sum+=x;
        }
        if(!sum.equals(projectExpense)){
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

    public static void getProjectInformation() {
        try {
            getProjectId();
            getProjectName();
            getProjectSource();
            getProjectType();
            getProjectExpense();
            getProjectStartYear();
            getProjectEndYear();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
        }
    }
}
