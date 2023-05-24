package priv.jeffrey.trrs.backend;

import java.sql.*;

public class DBConnector {
    protected static final String JAVA_DATABASE_CONNECTOR_DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static final String DATABASE_UNIFORM_RESOURCE_LOCATOR = "jdbc:mysql://localhost:3306/trrs?useSSL=false&allowPublicKeyRetrieval=true" +
             "&serverTimezone=UTC";
    protected static final String USERNAME = "user_select";
    protected static final String PASSWORD = " ";
    protected Connection connection = null;
    protected static PreparedStatement preparedStatement = null;
    protected static ResultSet resultSet = null;

    protected static String structuredQueryLanguage ="";
    protected static void setConnection()throws SQLException, ClassNotFoundException{
        Class.forName(JAVA_DATABASE_CONNECTOR_DRIVER);
        connection = DriverManager.getConnection(DATABASE_UNIFORM_RESOURCE_LOCATOR, USERNAME, PASSWORD);
        preparedStatement = connection.prepareStatement(structuredQueryLanguage);
    }
    protected static void closeConnection()throws SQLException{
        preparedStatement.close();
        connection.close();
    }
}