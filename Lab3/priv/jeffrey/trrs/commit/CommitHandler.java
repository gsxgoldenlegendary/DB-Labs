package priv.jeffrey.trrs.commit;

import priv.jeffrey.trrs.utilities.DatabaseConnector;

import java.sql.SQLException;
import java.util.Vector;

public class CommitHandler extends DatabaseConnector {
    private static final String PROJECT_ADD_ROUTINE = "CALL projectAdd(?,?,?,?,?,?,?)";
    private static final String COMMIT_ADD_ROUTINE = "CALL commitAdd(?,?,?,?)";
    private static final String PROJECT_UPDATE_ROUTINE = "CALL projectUpdate(?,?,?,?,?,?,?)";
    private static final String COMMIT_UPDATE_ROUTINE = "CALL commitUpdate(?,?,?,?)";
    private static final String COMMIT_DELETE_ROUTINE = "CALL commitDelete(?)";
    private static final String COMMIT_QUERY_ROUTINE = "SELECT * FROM commit WHERE project_id = ?";
    private static final String PROJECT_QUERY_ROUTINE = "SELECT * FROM project WHERE id = ?";

    @Override
    public void insert(Vector<Vector<String>> panelInfo) throws SQLException {
        actionAddUpdate(
                panelInfo.get(0).get(0),
                panelInfo.get(0).get(1),
                panelInfo.get(0).get(2),
                Integer.parseInt(panelInfo.get(0).get(3)),
                Float.parseFloat(panelInfo.get(0).get(4)),
                Integer.parseInt(panelInfo.get(0).get(5)),
                Integer.parseInt(panelInfo.get(0).get(6)),
                panelInfo.get(1),
                panelInfo.get(2),
                true
        );
    }

    @Override
    public void update(Vector<Vector<String>> panelInfo) throws SQLException {
        actionAddUpdate(
                panelInfo.get(0).get(0),
                panelInfo.get(0).get(1),
                panelInfo.get(0).get(2),
                Integer.parseInt(panelInfo.get(0).get(3)),
                Float.parseFloat(panelInfo.get(0).get(4)),
                Integer.parseInt(panelInfo.get(0).get(5)),
                Integer.parseInt(panelInfo.get(0).get(6)),
                panelInfo.get(1),
                panelInfo.get(2),
                false
        );
    }

    @Override
    public void delete(Vector<Vector<String>> panelInfo) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(COMMIT_DELETE_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Vector<Vector<Object>> search(Vector<Vector<String>> panelInfo) throws SQLException {
        Vector<Vector<Object>> result = new Vector<>();
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PROJECT_QUERY_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("id"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("source"));
                row.add(resultSet.getString("type"));
                row.add(resultSet.getString("funding"));
                row.add(resultSet.getString("start_year"));
                row.add(resultSet.getString("end_year"));
                result.add(row);
            }
            preparedStatement = connection.prepareStatement(COMMIT_QUERY_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("project_id"));
                row.add(resultSet.getString("teacher_id"));
                row.add(resultSet.getString("ranking"));
                row.add(resultSet.getString("commit_funding"));
                result.add(row);
            }
            resultSet.close();
            preparedStatement.close();
            System.out.println(result);
            return result;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public static void actionAddUpdate(
            String projectId,
            String projectName,
            String projectSource,
            int projectType,
            Float projectExpense,
            int projectStartYear,
            int projectEndYear,
            Vector<String> teacherIdList,
            Vector<String> teacherExpenseList,
            boolean isAdd
    ) throws SQLException {
        try {
            setConnection();
            if (isAdd) {
                preparedStatement = connection.prepareStatement(PROJECT_ADD_ROUTINE);
            } else {
                preparedStatement = connection.prepareStatement(PROJECT_UPDATE_ROUTINE);
            }
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
                if (isAdd) {
                    preparedStatement = connection.prepareStatement(COMMIT_ADD_ROUTINE);
                } else {
                    preparedStatement = connection.prepareStatement(COMMIT_UPDATE_ROUTINE);
                }
                preparedStatement.setString(1, projectId);
                preparedStatement.setString(2, teacherId);
                preparedStatement.setInt(3, count);
                preparedStatement.setFloat(4, Float.parseFloat(teacherExpenseList.get(count - 1)));
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
