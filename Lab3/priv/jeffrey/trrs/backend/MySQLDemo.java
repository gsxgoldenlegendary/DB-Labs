package priv.jeffrey.trrs.backend;

import java.sql.*;

public class MySQLDemo {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/trrs?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "user_select";
    static final String PASS = " ";

    public static String query(String id) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM teacher WHERE id = " + id;
            rs = stmt.executeQuery(sql);
            rs.next();
            String tmp=rs.getString("name");
            rs.close();
            stmt.close();
            conn.close();
            return tmp;
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
       return "error";
    }

    public static void main(String[] args) {

    }
}