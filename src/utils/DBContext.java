package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "PizzaStore";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";
    
    private static final String URL = "jdbc:sqlserver://" + SERVER + ":" + PORT + 
                                     ";databaseName=" + DATABASE + ";encrypt=false;trustServerCertificate=true";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server JDBC Driver not found. Please add mssql-jdbc jar to WEB-INF/lib");
            throw new SQLException("SQL Server JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Cannot connect to database. Please check:");
            System.err.println("1. SQL Server is running");
            System.err.println("2. Database 'PizzaStore' exists");
            System.err.println("3. Connection credentials are correct");
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}