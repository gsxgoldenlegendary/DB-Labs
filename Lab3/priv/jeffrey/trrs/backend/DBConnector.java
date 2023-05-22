package priv.jeffrey.trrs.backend;

import java.sql.*;

public class DBConnector {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/trrs?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "user_select";
    static final String PASS = " ";


    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    String sql="";
    public static void main(String[] args) {

    }
}