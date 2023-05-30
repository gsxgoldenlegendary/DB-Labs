package priv.jeffrey.trrs.backend;

import java.sql.*;
import java.util.Vector;

public class DBConnector {
    protected static final String JAVA_DATABASE_CONNECTOR_DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static final String DATABASE_UNIFORM_RESOURCE_LOCATOR = "jdbc:mysql://localhost:3306/trrs?useSSL=false&allowPublicKeyRetrieval=true" +
             "&serverTimezone=UTC";
    protected static final String USERNAME = "user_select";
    protected static final String PASSWORD = " ";
    protected static Connection connection = null;
    protected static PreparedStatement preparedStatement = null;
    protected static ResultSet resultSet = null;

    protected static void setConnection()throws SQLException, ClassNotFoundException{
        Class.forName(JAVA_DATABASE_CONNECTOR_DRIVER);
        connection = DriverManager.getConnection(DATABASE_UNIFORM_RESOURCE_LOCATOR, USERNAME, PASSWORD);
    }
    protected static void closeConnection()throws SQLException{
        preparedStatement.close();
        connection.close();
    }
    public void add(Vector<Vector<Object>> panelInfoVectorVector)throws SQLException{



    }
    public void update(Vector<Vector<Object>> panelInfoArray)throws SQLException{

    }
    public void delete(Vector<Vector<Object>> panelInfoArray)throws SQLException{

    }
    public Vector<Vector<Object>> search(Vector<Vector<Object>> panelInfoArray)throws SQLException{
        return null;
    }
}