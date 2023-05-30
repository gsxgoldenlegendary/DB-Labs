package priv.jeffrey.trrs.backend;

import java.sql.SQLException;
import java.util.Vector;

public class TeachHandler extends DBConnector {
    private static final String COURSE_ADD_ROUTINE="CALL course_add(?,?,?,?)";
    private static final String TEACH_ADD_ROUTINE="CALL teach_add(?,?,?,?,?,?)";
    private static final String COURSE_UPDATE_ROUTINE="CALL course_update(?,?,?,?)";
    private static final String TEACH_UPDATE_ROUTINE="CALL teach_update(?,?,?,?,?,?)";
    private static final String TEACH_DELETE_ROUTINE="CALL teach_delete(?)";
    private static final String COURSE_SEARCH_ROUTINE="SELECT * FROM course WHERE id = ?";
    private static final String TEACH_SEARCH_ROUTINE="SELECT * FROM teach WHERE course_id = ?";
    public static void actionAdd(
            String courseId,
            String courseName,
            int courseTotalHours,
            int courseProperty,
            int courseYear,
            int courseTerm,
            Vector<String> teacherIdList,
            Vector<Integer> teacherHoursList
    ) throws SQLException{
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(COURSE_ADD_ROUTINE);
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, courseName);
            preparedStatement.setInt(3, courseTotalHours);
            preparedStatement.setInt(4, courseProperty);
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            for (int i = 0; i < teacherIdList.size(); i++) {
                preparedStatement = connection.prepareStatement(TEACH_ADD_ROUTINE);
                preparedStatement.setString(1, courseId);
                preparedStatement.setString(2, teacherIdList.get(i));
                preparedStatement.setInt(3, courseYear);
                preparedStatement.setInt(4, courseTerm);
                preparedStatement.setInt(5, teacherHoursList.get(i));
                preparedStatement.setInt(6,i);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            connection.commit();
        }catch (SQLException e) {
            connection.rollback();
            throw e;
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
    }
    public static void actionUpdate(
            String courseId,
            String courseName,
            int courseTotalHours,
            int courseProperty,
            int courseYear,
            int courseTerm,
            Vector<String> teacherIdList,
            Vector<Integer> teacherHoursList
    ) throws SQLException{
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(COURSE_UPDATE_ROUTINE);
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, courseName);
            preparedStatement.setInt(3, courseTotalHours);
            preparedStatement.setInt(4, courseProperty);
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            for (int i = 0; i < teacherIdList.size(); i++) {
                preparedStatement = connection.prepareStatement(TEACH_UPDATE_ROUTINE);
                preparedStatement.setString(1, courseId);
                preparedStatement.setString(2, teacherIdList.get(i));
                preparedStatement.setInt(3, courseYear);
                preparedStatement.setInt(4, courseTerm);
                preparedStatement.setInt(5, teacherHoursList.get(i));
                preparedStatement.setInt(6,i);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            connection.commit();
        }catch (SQLException e) {
            connection.rollback();
            throw e;
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
    }

    public static void actionDelete(
            String courseId
    ) throws SQLException{
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(TEACH_DELETE_ROUTINE);
            preparedStatement.setString(1, courseId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
    }

    public static Vector<Vector<String>> actionSearch(String projectId) throws SQLException {
        try {
            setConnection();
            Vector<Vector<String>> result = new Vector<>();
            preparedStatement = connection.prepareStatement(COURSE_SEARCH_ROUTINE);
            preparedStatement.setString(1, projectId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("id"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("hours"));
                row.add(resultSet.getString("property"));
                result.add(row);
            }
            preparedStatement = connection.prepareStatement(TEACH_SEARCH_ROUTINE);
            preparedStatement.setString(1, projectId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("teacher_id"));
                row.add(resultSet.getString("commit_hours"));
                row.add(resultSet.getString("year"));
                row.add(resultSet.getString("semester"));
                result.add(row);
            }
            return result;
        }  catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
