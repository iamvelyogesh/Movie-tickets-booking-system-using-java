package movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLJDBCConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dbname";
    private static final String USERNAME = "dbusername";
    private static final String PASSWORD = "dbpassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
