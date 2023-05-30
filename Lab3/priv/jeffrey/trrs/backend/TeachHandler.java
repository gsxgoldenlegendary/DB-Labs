package priv.jeffrey.trrs.backend;

import java.sql.SQLException;
import java.util.Vector;

public class TeachHandler extends DBConnector {
    private static final String COURSE_ADD_ROUTINE="CALL course_add(?,?,?,?)";
    private static final String TEACH_ADD_ROUTINE="CALL teach_add(?,?,?,?,?,?)";
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
}
