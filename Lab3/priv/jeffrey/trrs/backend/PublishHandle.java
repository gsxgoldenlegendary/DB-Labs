package priv.jeffrey.trrs.backend;

import java.sql.*;

public class PublishHandle extends DBConnector {

    public void operateAdd(String teacherId,
                           int teacherRanking,
                           int correspondingAuthor,
                           int paperId,
                           String paperTitle,
                           String paperSource,
                           int paperYear,
                           int paperType,
                           int paperLevel) throws SQLException {
        sql = "CALL publishAdd(?,?,?,?,?,?,?,?,?)";
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, paperId);
            preparedStatement.setString(2, paperTitle);
            preparedStatement.setString(3, paperSource);
            preparedStatement.setInt(4, paperYear);
            preparedStatement.setInt(5, paperType);
            preparedStatement.setInt(6, paperLevel);
            preparedStatement.setString(7, teacherId);
            preparedStatement.setInt(8, teacherRanking);
            preparedStatement.setInt(9, correspondingAuthor);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void operateDelete(String teacherId,
                           int paperId
                          ) throws SQLException {
        sql = "CALL publishDelete(?,?)";
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, paperId);
            preparedStatement.setString(2, teacherId);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
