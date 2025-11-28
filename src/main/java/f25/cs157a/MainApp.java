package f25.cs157a;

import java.sql.*;
import java.util.Scanner;
import f25.cs157a.db.DBConnector;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection conn = null;

    public static void main(String[] args) {
        System.out.println("Connecting to database...");
        conn = DBConnector.getConnection();

        if (conn == null) {
            System.out.println("Connection failed.");
            return;
        }
        System.out.println("Connected!\n");

        boolean running = true;
        while (running) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. View Data");
            System.out.println("2. Insert Data");
            System.out.println("3. Update Data");
            System.out.println("4. Delete Data");
            System.out.println("5. Run Transaction");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": viewMenu(); break;
                case "2": insertMenu(); break;
                case "3": updateMenu(); break;
                case "4": deleteMenu(); break;
                case "5": runTransaction(); break;
                case "6": running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }

        DBConnector.closeConnection(conn);
        System.out.println("Goodbye!");
    }

    private static void viewMenu() {
        System.out.println("\n=== VIEW DATA ===");
        System.out.println("1. Students");
        System.out.println("2. Rooms");
        System.out.println("3. MaintenanceTickets");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        try {
            String sql = "";
            switch (choice) {
                case "1": sql = "SELECT * FROM Students"; break;
                case "2": sql = "SELECT * FROM Rooms"; break;
                case "3": sql = "SELECT * FROM MaintenanceTickets"; break;
                default: System.out.println("Invalid choice."); return;
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                System.out.print(meta.getColumnName(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void insertMenu() {
        System.out.println("\n=== INSERT DATA ===");
        System.out.println("1. Student");
        System.out.println("2. Room");
        System.out.println("3. MaintenanceTicket");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1":
                    System.out.print("StudentID: ");
                    int sid = Integer.parseInt(scanner.nextLine());
                    System.out.print("FirstName: ");
                    String fname = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("FinancialAidStatus (true/false): ");
                    boolean aid = Boolean.parseBoolean(scanner.nextLine());
                    System.out.print("MealPlanID: ");
                    int mpid = Integer.parseInt(scanner.nextLine());

                    PreparedStatement ps1 = conn.prepareStatement(
                        "INSERT INTO Students VALUES (?, ?, ?, ?, ?)");
                    ps1.setInt(1, sid);
                    ps1.setString(2, fname);
                    ps1.setString(3, email);
                    ps1.setBoolean(4, aid);
                    ps1.setInt(5, mpid);
                    ps1.executeUpdate();
                    ps1.close();
                    System.out.println("Student inserted.");
                    break;

                case "2":
                    System.out.print("RoomID: ");
                    int rid = Integer.parseInt(scanner.nextLine());
                    System.out.print("DormID: ");
                    int did = Integer.parseInt(scanner.nextLine());
                    System.out.print("RoomNumber: ");
                    int rnum = Integer.parseInt(scanner.nextLine());

                    PreparedStatement ps2 = conn.prepareStatement(
                        "INSERT INTO Rooms VALUES (?, ?, ?)");
                    ps2.setInt(1, rid);
                    ps2.setInt(2, did);
                    ps2.setInt(3, rnum);
                    ps2.executeUpdate();
                    ps2.close();
                    System.out.println("Room inserted.");
                    break;

                case "3":
                    System.out.print("TicketID: ");
                    int tid = Integer.parseInt(scanner.nextLine());
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("TechnicianID: ");
                    int techid = Integer.parseInt(scanner.nextLine());
                    System.out.print("Cost: ");
                    double cost = Double.parseDouble(scanner.nextLine());
                    System.out.print("Status: ");
                    String status = scanner.nextLine();
                    System.out.print("CreationDate (YYYY-MM-DD): ");
                    String cdate = scanner.nextLine();

                    PreparedStatement ps3 = conn.prepareStatement(
                        "INSERT INTO MaintenanceTickets (TicketID, Description, TechnicianID, Cost, Status, CreationDate) VALUES (?, ?, ?, ?, ?, ?)");
                    ps3.setInt(1, tid);
                    ps3.setString(2, desc);
                    ps3.setInt(3, techid);
                    ps3.setDouble(4, cost);
                    ps3.setString(5, status);
                    ps3.setDate(6, Date.valueOf(cdate));
                    ps3.executeUpdate();
                    ps3.close();
                    System.out.println("Ticket inserted.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateMenu() {
        System.out.println("\n=== UPDATE DATA ===");
        System.out.println("1. Update Student MealPlan");
        System.out.println("2. Update Ticket Status");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1":
                    System.out.print("StudentID: ");
                    int sid = Integer.parseInt(scanner.nextLine());
                    System.out.print("New MealPlanID: ");
                    int mpid = Integer.parseInt(scanner.nextLine());

                    PreparedStatement ps1 = conn.prepareStatement(
                        "UPDATE Students SET MealPlanID = ? WHERE StudentID = ?");
                    ps1.setInt(1, mpid);
                    ps1.setInt(2, sid);
                    int rows1 = ps1.executeUpdate();
                    ps1.close();
                    System.out.println(rows1 + " row(s) updated.");
                    break;

                case "2":
                    System.out.print("TicketID: ");
                    int tid = Integer.parseInt(scanner.nextLine());
                    System.out.print("New Status: ");
                    String status = scanner.nextLine();

                    PreparedStatement ps2 = conn.prepareStatement(
                        "UPDATE MaintenanceTickets SET Status = ? WHERE TicketID = ?");
                    ps2.setString(1, status);
                    ps2.setInt(2, tid);
                    int rows2 = ps2.executeUpdate();
                    ps2.close();
                    System.out.println(rows2 + " row(s) updated.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteMenu() {
        System.out.println("\n=== DELETE DATA ===");
        System.out.println("1. Delete Student");
        System.out.println("2. Delete Ticket");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1":
                    System.out.print("StudentID to delete: ");
                    int sid = Integer.parseInt(scanner.nextLine());

                    PreparedStatement ps1 = conn.prepareStatement(
                        "DELETE FROM Students WHERE StudentID = ?");
                    ps1.setInt(1, sid);
                    int rows1 = ps1.executeUpdate();
                    ps1.close();
                    System.out.println(rows1 + " row(s) deleted.");
                    break;

                case "2":
                    System.out.print("TicketID to delete: ");
                    int tid = Integer.parseInt(scanner.nextLine());

                    PreparedStatement ps2 = conn.prepareStatement(
                        "DELETE FROM MaintenanceTickets WHERE TicketID = ?");
                    ps2.setInt(1, tid);
                    int rows2 = ps2.executeUpdate();
                    ps2.close();
                    System.out.println(rows2 + " row(s) deleted.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void runTransaction() {
        System.out.println("\n=== TRANSACTION: Assign Student to Room ===");

        try {
            System.out.print("AssignmentID: ");
            int aid = Integer.parseInt(scanner.nextLine());
            System.out.print("StudentID: ");
            int sid = Integer.parseInt(scanner.nextLine());
            System.out.print("RoomID: ");
            int rid = Integer.parseInt(scanner.nextLine());
            System.out.print("LeaseStartDate (YYYY-MM-DD): ");
            String start = scanner.nextLine();
            System.out.print("LeaseEndDate (YYYY-MM-DD): ");
            String end = scanner.nextLine();

            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(
                "INSERT INTO RoomAssignments VALUES (?, ?, ?, ?, ?, ?)");
            ps1.setInt(1, aid);
            ps1.setInt(2, sid);
            ps1.setInt(3, rid);
            ps1.setDate(4, Date.valueOf(start));
            ps1.setDate(5, Date.valueOf(end));
            ps1.setBoolean(6, true);
            ps1.executeUpdate();
            ps1.close();
            System.out.println("RoomAssignment inserted.");

            System.out.print("Create maintenance ticket for room? (yes/no): ");
            String createTicket = scanner.nextLine();

            if (createTicket.equalsIgnoreCase("yes")) {
                System.out.print("TicketID: ");
                int tid = Integer.parseInt(scanner.nextLine());
                System.out.print("Description: ");
                String desc = scanner.nextLine();
                System.out.print("TechnicianID: ");
                int techid = Integer.parseInt(scanner.nextLine());
                System.out.print("Cost: ");
                double cost = Double.parseDouble(scanner.nextLine());

                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO MaintenanceTickets (TicketID, Description, TechnicianID, Cost, Status, CreationDate) VALUES (?, ?, ?, ?, 'Open', CURDATE())");
                ps2.setInt(1, tid);
                ps2.setString(2, desc);
                ps2.setInt(3, techid);
                ps2.setDouble(4, cost);
                ps2.executeUpdate();
                ps2.close();
                System.out.println("MaintenanceTicket inserted.");

                PreparedStatement ps3 = conn.prepareStatement(
                    "INSERT INTO Requires VALUES (?, ?)");
                ps3.setInt(1, tid);
                ps3.setInt(2, rid);
                ps3.executeUpdate();
                ps3.close();
                System.out.println("Requires relationship created.");
            }

            System.out.print("Commit? (yes/no): ");
            String commit = scanner.nextLine();

            if (commit.equalsIgnoreCase("yes")) {
                conn.commit();
                System.out.println("COMMITTED.");
            } else {
                conn.rollback();
                System.out.println("ROLLED BACK.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            try {
                conn.rollback();
                System.out.println("ROLLED BACK due to error.");
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error resetting auto-commit.");
            }
        }
    }
}
