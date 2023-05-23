package priv.jeffrey.trrs.backend;

import java.sql.*;

public class PublishHandle extends DBConnector {

    private static final String PUBLISH_ADD_ROUTINE ="CALL publishAdd(?,?,?,?,?,?,?,?,?)";
    private static final String publishDeleteRoutine="CALL publishDelete(?,?)";
    private static final String publishUpdateRoutine="CALL publishUpdate(?,?,?,?,?,?,?,?,?)";

    public void operateAdd(String teacherId,
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
    public void operateDelete(String teacherId,
                           int paperId
                          ) throws SQLException {
        structuredQueryLanguage = publishDeleteRoutine;
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
    public void operateUpdate(String teacherId,
                           int teacherRanking,
                           int correspondingAuthor,
                           int paperId,
                           String paperTitle,
                           String paperSource,
                           int paperYear,
                           int paperType,
                           int paperLevel) throws SQLException {
        structuredQueryLanguage = publishUpdateRoutine;
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
}
