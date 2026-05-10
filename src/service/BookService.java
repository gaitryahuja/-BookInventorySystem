package service;

import model.Book;
import java.sql.*;

public class BookService implements BaseService {

    //insert book
    public boolean addBook(Book b) {
        String sql = "INSERT INTO Book(title, author, category, price, quantity) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
           
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getCategory());
            ps.setDouble(4, b.getPrice());
            ps.setInt(5, b.getQuantity());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Success: Book has been added to the inventory.");
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.out.println("Error: Failed to add book. " + e.getMessage());
            return false;
        }
    }


    //Search book
    public Book searchBook(int id) {
        String sql = "SELECT * FROM Book WHERE book_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: Search operation failed. " + e.getMessage());
        }
        return null;
    }


    //Update book
    public boolean updateBook(Book b) {
        String sql = "UPDATE Book SET title = ?, author = ?, category = ?, price = ?, quantity = ? WHERE book_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getCategory());
            ps.setDouble(4, b.getPrice());
            ps.setInt(5, b.getQuantity());
            ps.setInt(6, b.getBookId());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Success: Book details updated.");
                return true;
            } else {
                System.out.println("Error: Book ID not found for update.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: Update failed. " + e.getMessage());
            return false;
        }
    }


    //Delete book
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM Book WHERE book_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Success: Book deleted from inventory.");
                return true;
            } else {
                System.out.println("Error: Book ID " + id + " does not exist.");
                return false;
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint")) {
                System.out.println("Error: Cannot delete this book because it is linked to existing Sales or Purchases.");
            } else {
                System.out.println("Error: Delete operation failed. " + e.getMessage());
            }
            return false;
        }
    }

    //shows all th books
    @Override
    public void listAll() {
        String sql = "SELECT * FROM Book";
        
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Current Inventory ---");
            System.out.println("ID | Title | Author | Category | Price | Stock");
            System.out.println("------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | " +
                    rs.getString("category") + " | " +
                    rs.getDouble("price") + " | " +
                    rs.getInt("quantity")
                );
            }
            
            if (!hasData) {
                System.out.println("Inventory is currently empty.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: List display failed. " + e.getMessage());
        }
    }
}