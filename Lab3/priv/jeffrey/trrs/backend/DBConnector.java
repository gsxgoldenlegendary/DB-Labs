package priv.jeffrey.trrs.backend;

import java.sql.*;

public class DBConnector {
    protected static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/trrs?useSSL=false&allowPublicKeyRetrieval=true" +
             "&serverTimezone=UTC";
    protected static final String USER = "user_select";
    protected static final String PASS = " ";
    protected Connection connection = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;

    protected String sql="";
    public static void main(String[] args) {

    }
}