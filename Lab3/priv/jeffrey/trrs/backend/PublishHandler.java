package priv.jeffrey.trrs.backend;

import java.sql.*;
import java.util.Vector;

public class PublishHandler extends DBConnector {

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
        structuredQueryLanguage = PUBLISH_ADD_ROUTINE;
        try {
            setConnection();
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

    public static void operateDelete(String teacherId,
                              int paperId
    ) throws SQLException {
        structuredQueryLanguage = PUBLISH_DELETE_ROUTINE;
        try {
            setConnection();
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
        structuredQueryLanguage = PUBLISH_UPDATE_ROUTINE;
        try {
            setConnection();
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
        structuredQueryLanguage = PUBLISH_QUERY_ROUTINE;
        try {
            setConnection();
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
