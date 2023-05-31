package priv.jeffrey.trrs.teach;

import priv.jeffrey.trrs.utilities.DatabaseConnector;

import java.sql.SQLException;
import java.util.Vector;

public class TeachHandler extends DatabaseConnector {
    private static final String COURSE_ADD_ROUTINE = "CALL course_add(?,?,?,?)";
    private static final String TEACH_ADD_ROUTINE = "CALL teach_add(?,?,?,?,?,?)";
    private static final String COURSE_UPDATE_ROUTINE = "CALL course_update(?,?,?,?)";
    private static final String TEACH_UPDATE_ROUTINE = "CALL teach_update(?,?,?,?,?,?)";
    private static final String TEACH_DELETE_ROUTINE = "CALL teach_delete(?)";
    private static final String COURSE_SEARCH_ROUTINE = "SELECT * FROM course WHERE id = ?";
    private static final String TEACH_SEARCH_ROUTINE = "SELECT * FROM teach WHERE course_id = ?";

    @Override
    public void insert(Vector<Vector<String>> panelInfo) throws SQLException {
        actionAddUpdate(
                panelInfo.get(0).get(0),
                panelInfo.get(0).get(1),
                Integer.parseInt(panelInfo.get(0).get(2)),
                Integer.parseInt(panelInfo.get(0).get(3)),
                Integer.parseInt(panelInfo.get(0).get(4)),
                Integer.parseInt(panelInfo.get(0).get(5)),
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
                Integer.parseInt(panelInfo.get(0).get(2)),
                Integer.parseInt(panelInfo.get(0).get(3)),
                Integer.parseInt(panelInfo.get(0).get(4)),
                Integer.parseInt(panelInfo.get(0).get(5)),
                panelInfo.get(1),
                panelInfo.get(2),
                false
        );
    }

    @Override
    public void delete(Vector<Vector<String>> panelInfo) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(TEACH_DELETE_ROUTINE);
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
            preparedStatement = connection.prepareStatement(COURSE_SEARCH_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("id"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getInt("hours"));
                row.add(resultSet.getInt("property"));
                result.add(row);
            }
            preparedStatement = connection.prepareStatement(TEACH_SEARCH_ROUTINE);
            preparedStatement.setString(1, panelInfo.get(0).get(0));
            resultSet = preparedStatement.executeQuery();
            boolean flag = false;
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("teacher_id"));
                row.add(resultSet.getInt("commit_hours"));
                if (!flag) {
                    result.get(0).add(resultSet.getInt("year"));
                    result.get(0).add(resultSet.getInt("semester"));
                    flag = true;
                }
                result.add(row);
            }
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }

    public static void actionAddUpdate(
            String courseId,
            String courseName,
            int courseTotalHours,
            int courseProperty,
            int courseYear,
            int courseTerm,
            Vector<String> teacherIdList,
            Vector<String> teacherHoursList,
            boolean isAdd
    ) throws SQLException {
        try {
            setConnection();
            if (isAdd) {
                preparedStatement = connection.prepareStatement(COURSE_ADD_ROUTINE);
            } else {
                preparedStatement = connection.prepareStatement(COURSE_UPDATE_ROUTINE);
            }
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, courseName);
            preparedStatement.setInt(3, courseTotalHours);
            preparedStatement.setInt(4, courseProperty);
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            for (int i = 0; i < teacherIdList.size(); i++) {
                if (isAdd) {
                    preparedStatement = connection.prepareStatement(TEACH_ADD_ROUTINE);
                } else {
                    preparedStatement = connection.prepareStatement(TEACH_UPDATE_ROUTINE);
                }
                preparedStatement.setString(1, courseId);
                preparedStatement.setString(2, teacherIdList.get(i));
                preparedStatement.setInt(3, courseYear);
                preparedStatement.setInt(4, courseTerm);
                preparedStatement.setInt(5, Integer.parseInt(teacherHoursList.get(i)));
                preparedStatement.setInt(6, i);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
    }
}
