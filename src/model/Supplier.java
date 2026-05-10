package model;

// Extension of person class

public class Supplier extends Person {
    
    private int supplierId;

    //Constructor 1
    public Supplier(int supplierId, String name, String contact, String address) {
        super(name, contact, address); 
        this.supplierId = supplierId;
    }

    //Constructor 2
    public Supplier(String name, String contact, String address) {
        super(name, contact, address);
    }

    // Setter and Getter
    public int getSupplierId() { 
        return supplierId; 
    }
    
    public void setSupplierId(int supplierId) { 
        this.supplierId = supplierId; 
    }

    //To string override
    @Override
    public String toString() {
        return "Supplier [ID=" + supplierId + ", " + super.toString() + "]";
    }
}