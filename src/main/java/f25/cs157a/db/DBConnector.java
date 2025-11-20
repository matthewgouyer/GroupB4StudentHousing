package f25.cs157a.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

// simple db connection class similar to one I learned about in cs151
public class DBConnector {
    private static final String CONFIG_FILE = "app.properties";

    /**
     * Reads DB config from app.properties and establishes a Connection.
     * @return A valid java.sql.Connection object, or null if connection fails.
     */
    public static Connection getConnection() {
        Properties props = new Properties();

        try (InputStream input = DBConnector.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Error: Configuration file '" + CONFIG_FILE + "' not found. Place it in src/main/resources.");
                return null;
            }
            props.load(input);
        } catch (Exception e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            return null;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            System.err.println("Error Code: " + e.getErrorCode() + ", Message: " + e.getMessage());
            return null;
        }
    }

    /** Helper method to safely close a connection */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignore) { /* ignore */ }
        }
    }

    /** Helper method to safely close a PreparedStatement */
    public static void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignore) { /* ignore */ }
        }
    }
}