package model;

//It extends the Person class. Usage of Inheritance.
public class Customer extends Person {

    private int customerId;

    //Use this to load customer from database
    public Customer(int customerId, String name, String contact, String address) {
        super(name, contact, address); 
        this.customerId = customerId;
    }

    //Constructor for adding new customer
    public Customer(String name, String contact, String address) {
        super(name, contact, address);
    }

    //Setter and Getter
    public int getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }

    //Displays Customer Information.
    @Override
    public String toString() {
        return "Customer [ID=" + customerId + ", " + super.toString() + "]";
    }
}