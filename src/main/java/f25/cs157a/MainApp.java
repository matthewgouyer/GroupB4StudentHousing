package f25.cs157a;

import java.sql.Connection;
import f25.cs157a.db.DBConnector;

// dev
public class MainApp {

    public static void main(String[] args) {
        System.out.println("Attempting to connect to the database...");

        // test connection
        Connection conn = DBConnector.getConnection();

        if (conn != null) {
            System.out.println("Connection successfully!");
            // close connection after success
            DBConnector.closeConnection(conn);
            System.out.println("Connection closed.");
        } else {
            System.out.println("Connection failed.");
            System.out.println("Application exited.");
        }
    }
}