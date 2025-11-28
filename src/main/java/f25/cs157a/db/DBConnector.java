package f25.cs157a.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnector {
    private static final String CONFIG_FILE = "app.properties";

    public static Connection getConnection() {
        Properties props = new Properties();

        try (InputStream input = DBConnector.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Config file not found.");
                return null;
            }
            props.load(input);
        } catch (Exception e) {
            System.err.println("Error reading config: " + e.getMessage());
            return null;
        }

        String driverClass = props.getProperty("db.driver");
        try {
            Class.forName(driverClass);
            System.out.println("JDBC Driver loaded: " + driverClass);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + driverClass);
            return null;
        }

        try {
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) {}
        }
    }
}
