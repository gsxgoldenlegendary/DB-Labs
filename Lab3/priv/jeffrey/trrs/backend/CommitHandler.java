package priv.jeffrey.trrs.backend;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class CommitHandler extends DBConnector {
    private static final String PROJECT_ADD_ROUTINE = "CALL projectAdd(?,?,?,?,?,?,?)";
    private static final String COMMIT_ADD_ROUTINE = "CALL commitAdd(?,?,?,?)";

    public static void actionAdd(
            String projectId,
            String projectName,
            String projectSource,
            int projectType,
            Float projectExpense,
            int projectStartYear,
            int projectEndYear,
            Vector<String> teacherIdList,
            Vector<Float> teacherExpenseList
    ) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PROJECT_ADD_ROUTINE);
            preparedStatement.setString(1, projectId);
            preparedStatement.setString(2, projectName);
            preparedStatement.setString(3, projectSource);
            preparedStatement.setInt(4, projectType);
            preparedStatement.setFloat(5, projectExpense);
            preparedStatement.setInt(6, projectStartYear);
            preparedStatement.setInt(7, projectEndYear);

            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            int count = 1;
            for (String teacherId : teacherIdList) {
                preparedStatement = connection.prepareStatement(COMMIT_ADD_ROUTINE);
                preparedStatement.setString(1, projectId);
                preparedStatement.setString(2, teacherId);
                preparedStatement.setFloat(3, teacherExpenseList.get(count - 1));
                preparedStatement.setInt(4, count);
                preparedStatement.executeUpdate();
                count++;
            }
            connection.commit();

        } catch (SQLException e_sql) {
            connection.rollback();
            throw e_sql;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
    }
}
