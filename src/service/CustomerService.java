package service;

import model.Customer;
import java.sql.*;



public class CustomerService implements BaseService {

    //add customer
    public boolean addCustomer(Customer c) {
        String sql = "INSERT INTO Customer(name, contact, address) VALUES (?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, c.getName());
            ps.setString(2, c.getContact());
            ps.setString(3, c.getAddress());
            
            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Success: Customer registered successfully.");
                return true;
            } else {
                System.out.println("Error: Failed to register customer.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Database Error during add operation: " + e.getMessage());
            return false;
        }
    }

    //search customer
    public Customer searchCustomer(int id) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("customer_id"), 
                    rs.getString("name"), 
                    rs.getString("contact"), 
                    rs.getString("address")
                );
            } else {
                System.out.println("Result: No customer found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Database Error during search operation: " + e.getMessage());
        }
        return null;
    }

    //delete customer
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Success: Customer with ID " + id + " has been deleted.");
                return true;
            } else {
                System.out.println("Error: Customer ID " + id + " not found in the records.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Database Error during delete operation: " + e.getMessage());
            return false;
        }
    }

    //show all custumer
    @Override
    public void listAll() {
        String sql = "SELECT * FROM Customer";
        
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("\n--- Registered Customers List ---");
            System.out.println("ID | Name | Contact | Address");
            System.out.println("------------------------------------------------------------");
            
            boolean recordFound = false;
            while (rs.next()) {
                recordFound = true;
                int id = rs.getInt("customer_id");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String address = rs.getString("address");
                
                System.out.println(id + " | " + name + " | " + contact + " | " + address);
            }
            
            if (!recordFound) {
                System.out.println("Database Status: The customer table is currently empty.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error during list operation: " + e.getMessage());
        }
    }
}