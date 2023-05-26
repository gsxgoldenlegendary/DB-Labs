package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.backend.PublishHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;
import java.util.Vector;

import static priv.jeffrey.trrs.frontend.publish.DeletePanel.*;

public class DeleteAction {
    private static String teacherId;
    private static int paperId;
    private static JPanel panel;
    private static Vector<Vector<String>> queryResult;
    private static void showQueryResult() {
        if (queryResult == null) {
            JOptionPane.showMessageDialog(panel, "查询结果为空");
            return;
        }
        Object[] columns = {"教师工号", "论文序号", "作者排序", "是否为第一作者"};
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
            getTeacherId();
            queryResult = PublishHandler.operateQuery(teacherId);
            showQueryResult();
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(queryPanel, e_sql.getMessage());
            e_sql.printStackTrace();
        }
    }
    static void deleteActionPerformed(JPanel deletePanel) {
        panel = deletePanel;
        try {
            getTeacherId();
            getPaperId();
            PublishHandler.operateDelete(teacherId, paperId);
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
