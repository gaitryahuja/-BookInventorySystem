package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


//use of jdbc
public class DBConnection {

    // bookshop_db must be created in MySQL Server before running
    private static final String URL = "jdbc:mysql://localhost:3306/bookshop_db";
    private static final String USER = "root";
    
    private static String dbPassword = ""; 

    //ask user to enter password of their database
    public static boolean promptForPassword() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==================================================");
        System.out.println("       DATABASE CONFIGURATION SETUP");
        System.out.println("==================================================");
        System.out.print("Please enter your MySQL root password: ");
        
        String input = scanner.nextLine();

        
        if (input != null && !input.trim().isEmpty()) {
            dbPassword = input;
        } else {
            dbPassword = ""; 
        }
        
        // Immediately test the connection to verify the password
        return testConnection(); 
    }

    //exception handling for conection
    private static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Status: Connection to MySQL established successfully.");
                return true; // Password is correct
            }
        } catch (SQLException e) {
            // Detailed error handling for incorrect passwords or server issues
            System.out.println("\n[!] ACCESS DENIED: Database connection failed.");
            System.out.println("Error Message: " + e.getMessage());
            System.out.println("--------------------------------------------------");
        }
        return false; // Connection failed
    }


    public static Connection getConnection() throws SQLException {
        try {
             Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(URL, USER, dbPassword);
            
        } catch (ClassNotFoundException e) {

            throw new SQLException("JDBC Driver not found. Ensure that you my sql connector is in the classpath.");
        }
    }
}