package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.backend.PublishHandler;

import javax.swing.*;
import java.sql.SQLException;
import static priv.jeffrey.trrs.frontend.publish.AddPanel.*;


public class AddAction {
    private static String teacherId;
    private static int teacherRanking;
    private static int correspondingAuthor;
    private static int paperId;
    private static String paperTitle;
    private static String paperSource;
    private static int paperYear;
    private static int paperType;
    private static int paperLevel;
    private static JPanel panel;
    static void addActionPerformed(JPanel addPanel) {
        panel = addPanel;
        try {
            getTeacherInformation();
            getPaperInformation();
            PublishHandler.operateAdd(teacherId, teacherRanking, correspondingAuthor, paperId, paperTitle,
                    paperSource, paperYear, paperType, paperLevel);
            JOptionPane.showMessageDialog(panel, "论文发表情况登记成功");
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(panel, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }
    private static void getTeacherId() throws IllegalArgumentException {
        teacherId = teacherIdTextField.getText();
        if (teacherId.length() > 5 || teacherId.length() < 1) {
            JOptionPane.showMessageDialog(panel, "工号长度不合法");
            throw new IllegalArgumentException("工号长度不合法");
        }
    }

    private static void getTeacherRanking() throws IllegalArgumentException {
        try {
            teacherRanking = Integer.parseInt(teacherRankingTextField.getText());
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(panel, "作者排名不合法");
            throw new IllegalArgumentException("作者排名不合法");
        }
    }

    private static void getTeacherInformation() throws IllegalArgumentException {
        getTeacherId();
        getTeacherRanking();
    }

    private static void getPaperId() throws IllegalArgumentException {
        try {
            paperId = Integer.parseInt(paperIdTextField.getText());
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(panel, "论文序号不合法");
            throw new IllegalArgumentException("论文序号不合法");
        }
    }

    private static void getPaperTitle() throws IllegalArgumentException {
        paperTitle = paperTitleTextField.getText();
        if (paperTitle.length() > 255) {
            JOptionPane.showMessageDialog(panel, "论文标题过长");
            throw new IllegalArgumentException("论文标题过长");
        }
    }

    private static void getPaperSource() throws IllegalArgumentException {
        paperSource = paperSourceTextField.getText();
        if (paperSource.length() > 255) {
            JOptionPane.showMessageDialog(panel, "论文来源过长");
            throw new IllegalArgumentException("论文来源过长");
        }
    }

    private static void getPaperYear() throws IllegalArgumentException {
        try {
            paperYear = Integer.parseInt(paperYearTextField.getText());
            if (paperYear < 0 || paperYear > 2023) {
                JOptionPane.showMessageDialog(panel, "论文年份不合法");
                throw new IllegalArgumentException("论文年份不合法");
            }
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(panel, "论文年份不合法");
            throw new IllegalArgumentException("论文年份不合法");
        }
    }

    private static void getPaperInformation() throws IllegalArgumentException {
        getPaperId();
        getPaperTitle();
        getPaperSource();
        getPaperYear();
        paperType = paperTypeComboBox.getItemCount();
        paperLevel = paperLevelComboBox.getItemCount();
        correspondingAuthor = correspondingAuthorCheckBox.isSelected() ? 1 : 0;
    }
}
