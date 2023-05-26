package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.backend.PublishHandle;

import javax.swing.*;

import java.sql.SQLException;

import static priv.jeffrey.trrs.frontend.publish.DeletePanel.*;

public class DeleteAction {
    private static String teacherId;
    private static int paperId;
    private static JPanel panel;
    static void deleteActionPerformed(JPanel deletePanel) {
        panel = deletePanel;
        try {
            getTeacherId();
            getPaperId();
            PublishHandle.operateDelete(teacherId, paperId);
            JOptionPane.showMessageDialog(panel, "Publication Deleted");
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
    private static void getPaperId() throws IllegalArgumentException {
        try {
            paperId = Integer.parseInt(paperIdTextField.getText());
        } catch (NumberFormatException e_nfe) {
            JOptionPane.showMessageDialog(panel, "论文序号不合法");
            throw new IllegalArgumentException("论文序号不合法");
        }
    }
}
