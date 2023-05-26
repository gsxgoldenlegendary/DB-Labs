package priv.jeffrey.trrs.frontend.publish;

import priv.jeffrey.trrs.backend.PublishHandle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;
import java.util.Vector;

import static priv.jeffrey.trrs.frontend.publish.QueryPanel.*;

public class QueryAction {
    private static String teacherId;
    private static JPanel panel;
    private static Vector<Vector<String>>queryResult;


    static void queryActionPerformed(JPanel queryPanel) {
        panel = queryPanel;
        try{
            getTeacherId();
            queryResult= PublishHandle.operateQuery(teacherId);
            showQueryResult();
        } catch (IllegalArgumentException e_iae) {
            e_iae.printStackTrace();
        } catch (SQLException e_sql) {
            JOptionPane.showMessageDialog(queryPanel, e_sql.getMessage());
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
    private static void showQueryResult() {
        Object[] columns = {"教师工号", "论文序号", "作者排序", "是否为第一作者"};
        DefaultTableModel model = new DefaultTableModel(null, columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        for (Vector<String> row : queryResult) {
            model.addRow(row);
        }
        JOptionPane.showMessageDialog(panel, scrollPane);
    }
}
