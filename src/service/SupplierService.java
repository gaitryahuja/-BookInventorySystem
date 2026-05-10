package service;

import model.Supplier;
import java.sql.*;


public class SupplierService implements BaseService {


    public boolean addSupplier(Supplier s) {
        String sql = "INSERT INTO Supplier(name, contact, address) VALUES (?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, s.getName());
            ps.setString(2, s.getContact());
            ps.setString(3, s.getAddress());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Success: Supplier registered in the database.");
                return true;
            } else {
                System.out.println("Error: Failed to register supplier.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Database Error during add operation: " + e.getMessage());
            return false;
        }
    }

        public Supplier searchSupplier(int id) {
        String sql = "SELECT * FROM Supplier WHERE supplier_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Supplier(
                    rs.getInt("supplier_id"), 
                    rs.getString("name"), 
                    rs.getString("contact"), 
                    rs.getString("address")
                );
            } else {
                System.out.println("Result: No supplier found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Database Error during search operation: " + e.getMessage());
        }
        return null;
    }

    //delete 
    public boolean deleteSupplier(int id) {
        String sql = "DELETE FROM Supplier WHERE supplier_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Success: Supplier with ID " + id + " has been deleted.");
                return true;
            } else {
                System.out.println("Error: Supplier ID " + id + " not found in the records.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Database Error during delete operation: " + e.getMessage());
            return false;
        }
    }

    //listing
    @Override
    public void listAll() {
        String sql = "SELECT * FROM Supplier";
        
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("\n--- Registered Suppliers List ---");
            System.out.println("ID | Name | Contact | Address");
            System.out.println("------------------------------------------------------------");
            
            boolean recordFound = false;
            while (rs.next()) {
                recordFound = true;
                int id = rs.getInt("supplier_id");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String address = rs.getString("address");
                
                System.out.println(id + " | " + name + " | " + contact + " | " + address);
            }
            
            if (!recordFound) {
                System.out.println("Database Status: The supplier table is currently empty.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error during list operation: " + e.getMessage());
        }
    }
}