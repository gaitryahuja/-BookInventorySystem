package service;

import model.Purchase;
import java.sql.*;

public class PurchaseService implements BaseService {


    public boolean processPurchase(int bookId, int supplierId, int qty, double costPerUnit) {
        String insertPurchaseSql = "INSERT INTO Purchase(book_id, supplier_id, quantity, unit_cost, total_cost, purchase_date) VALUES (?, ?, ?, ?, ?, CURDATE())";
        String updateStockSql = "UPDATE Book SET quantity = quantity + ? WHERE book_id = ?";
        String checkBookSql = "SELECT title FROM Book WHERE book_id = ?";
        String checkSuppSql = "SELECT name FROM Supplier WHERE supplier_id = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); 

            try (PreparedStatement psBook = con.prepareStatement(checkBookSql)) {
                psBook.setInt(1, bookId);
                ResultSet rsBook = psBook.executeQuery();
                if (!rsBook.next()) {
                    System.out.println("Error: Book ID " + bookId + " does not exist in inventory.");
                    con.rollback(); // Cancel transaction
                    return false;
                }
            }

            try (PreparedStatement psSupp = con.prepareStatement(checkSuppSql)) {
                psSupp.setInt(1, supplierId);
                ResultSet rsSupp = psSupp.executeQuery();
                if (!rsSupp.next()) {
                    System.out.println("Error: Supplier ID " + supplierId + " does not exist.");
                    con.rollback(); // Cancel transaction
                    return false;
                }
            }

            //record
            try (PreparedStatement psPurchase = con.prepareStatement(insertPurchaseSql)) {
                psPurchase.setInt(1, bookId);
                psPurchase.setInt(2, supplierId);
                psPurchase.setInt(3, qty);
                psPurchase.setDouble(4, costPerUnit);
                psPurchase.setDouble(5, (costPerUnit * qty)); 
                psPurchase.executeUpdate();
            }

            //update
            try (PreparedStatement psUpdate = con.prepareStatement(updateStockSql)) {
                psUpdate.setInt(1, qty);
                psUpdate.setInt(2, bookId);
                psUpdate.executeUpdate();
            }

            //commit is also a database query used to final the result so it make sure the transaction is completed
            con.commit(); 
            System.out.println("Success: Purchase recorded and stock updated.");
            return true;

        } catch (SQLException e) {
            // If any error occurs during the steps, we undo everything
            if (con != null) {
                try {
                    System.out.println("Transaction failed. Rolling back changes...");
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Purchase Error: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    //listing
    @Override
    public void listAll() {
        String sql = "SELECT * FROM Purchase";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Purchase History (Restock Records) ---");
            System.out.println("ID | BookID | SuppID | Qty | Total | Date");
            System.out.println("------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("purchase_id") + " | " +
                    rs.getInt("book_id") + " | " +
                    rs.getInt("supplier_id") + " | " +
                    rs.getInt("quantity") + " | " +
                    rs.getDouble("total_cost") + " | " +
                    rs.getDate("purchase_date")
                );
            }
            
            if (!found) {
                System.out.println("Status: No purchases recorded.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: List Purchases failed. " + e.getMessage());
        }
    }
}