package priv.jeffrey.trrs.publish;

import priv.jeffrey.trrs.utilities.DatabaseConnector;

import java.sql.*;
import java.util.Vector;

public class PublishHandler extends DatabaseConnector {

    private static final String PUBLISH_ADD_ROUTINE = "CALL publishAdd(?,?,?,?,?,?,?,?,?)";
    private static final String PUBLISH_DELETE_ROUTINE = "CALL publishDelete(?,?)";
    private static final String PUBLISH_UPDATE_ROUTINE = "CALL publishUpdate(?,?,?,?,?,?,?,?,?)";
    private static final String PUBLISH_QUERY_ROUTINE = "SELECT * FROM publish WHERE publish.teacher_id = ?";

    public static void operateAdd(String teacherId,
                           int teacherRanking,
                           int correspondingAuthor,
                           int paperId,
                           String paperTitle,
                           String paperSource,
                           int paperYear,
                           int paperType,
                           int paperLevel) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PUBLISH_ADD_ROUTINE);
            preparedStatement.setInt(1, paperId);
            preparedStatement.setString(2, paperTitle);
            preparedStatement.setString(3, paperSource);
            preparedStatement.setInt(4, paperYear);
            preparedStatement.setInt(5, paperType);
            preparedStatement.setInt(6, paperLevel);
            preparedStatement.setString(7, teacherId);
            preparedStatement.setInt(8, teacherRanking);
            preparedStatement.setInt(9, correspondingAuthor);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
    }

    public static void operateDelete(String teacherId,
                              int paperId
    ) throws SQLException {

        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PUBLISH_DELETE_ROUTINE);
            preparedStatement.setInt(1, paperId);
            preparedStatement.setString(2, teacherId);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void operateUpdate(String teacherId,
                              int teacherRanking,
                              int correspondingAuthor,
                              int paperId,
                              String paperTitle,
                              String paperSource,
                              int paperYear,
                              int paperType,
                              int paperLevel) throws SQLException {
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PUBLISH_UPDATE_ROUTINE);
            preparedStatement.setInt(1, paperId);
            preparedStatement.setString(2, paperTitle);
            preparedStatement.setString(3, paperSource);
            preparedStatement.setInt(4, paperYear);
            preparedStatement.setInt(5, paperType);
            preparedStatement.setInt(6, paperLevel);
            preparedStatement.setString(7, teacherId);
            preparedStatement.setInt(8, teacherRanking);
            preparedStatement.setInt(9, correspondingAuthor);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vector<Vector<String>> operateQuery(String teacherId) throws SQLException {
        Vector<Vector<String>> result = new Vector<>();
        try {
            setConnection();
            preparedStatement = connection.prepareStatement(PUBLISH_QUERY_ROUTINE);
            preparedStatement.setString(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("teacher_id"));
                row.add(resultSet.getString("paper_id"));
                row.add(resultSet.getString("ranking"));
                row.add(resultSet.getString("is_corresponding_author"));
                result.add(row);
            }
            closeConnection();
            return result;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}