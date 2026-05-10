package service;

import model.Sale;
import model.Book;
import java.sql.*;



public class SaleService implements BaseService {

    public void processSale(int bookId, int customerId, int qty) {
        String insertSql = "INSERT INTO Sale(book_id, customer_id, quantity, total_price, sale_date) VALUES (?, ?, ?, ?, CURDATE())";
        String updateStockSql = "UPDATE Book SET quantity = quantity - ? WHERE book_id = ?";
        String checkBookSql = "SELECT price, quantity FROM Book WHERE book_id = ?";
        String checkCustSql = "SELECT name FROM Customer WHERE customer_id = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            
            con.setAutoCommit(false); 
 
            try (PreparedStatement psCust = con.prepareStatement(checkCustSql)) {
                psCust.setInt(1, customerId);
                ResultSet rsCust = psCust.executeQuery();
                if (!rsCust.next()) {
                    System.out.println("Error: Customer ID " + customerId + " not found.");
                    con.rollback();
                    return;
                }
            }


            double price = 0;
            int currentStock = 0;
            try (PreparedStatement psBook = con.prepareStatement(checkBookSql)) {
                psBook.setInt(1, bookId);
                ResultSet rsBook = psBook.executeQuery();
                if (rsBook.next()) {
                    price = rsBook.getDouble("price");
                    currentStock = rsBook.getInt("quantity");
                } else {
                    System.out.println("Error: Book ID " + bookId + " not found.");
                    con.rollback();
                    return;
                }
            }

            if (currentStock < qty) {
                System.out.println("Alert: Not enough stock! Current stock: " + currentStock);
                con.rollback();
                return;
            }

            double total = price * qty;

            try (PreparedStatement psSale = con.prepareStatement(insertSql)) {
                psSale.setInt(1, bookId);
                psSale.setInt(2, customerId);
                psSale.setInt(3, qty);
                psSale.setDouble(4, total);
                psSale.executeUpdate();
            }


            try (PreparedStatement psUpdate = con.prepareStatement(updateStockSql)) {
                psUpdate.setInt(1, qty);
                psUpdate.setInt(2, bookId);
                psUpdate.executeUpdate();
            }

            con.commit(); 
            System.out.println("Success: Sale processed. Total Price: " + total);

        } catch (SQLException e) {
            // If any database error occurs, undo all changes to prevent corrupted data
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Transaction Error: " + e.getMessage());
        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //listing
    @Override
    public void listAll() {
        String sql = "SELECT * FROM Sale";
        
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("\n--- Sales History ---");
            System.out.println("ID | Book ID | Customer ID | Quantity | Total | Date");
            System.out.println("------------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("sale_id");
                int bId = rs.getInt("book_id");
                int cId = rs.getInt("customer_id");
                int qty = rs.getInt("quantity");
                double total = rs.getDouble("total_price");
                Date date = rs.getDate("sale_date");

                System.out.println(id + " | " + bId + " | " + cId + " | " + qty + " | " + total + " | " + date);
            }
            
            if (!found) {
                System.out.println("Status: No sales records found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error during list operation: " + e.getMessage());
        }
    }
}