package model;
import java.sql.Date;

//Hold details about the items sold

public class Sale {

    private int saleId;
    private int bookId;
    private int customerId;
    private int quantity;
    private double totalPrice;
    private Date date;

    //Constructor
    public Sale(int saleId, int bookId, int customerId, int quantity, double totalPrice, Date date) {
        this.saleId = saleId;
        this.bookId = bookId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    //Setter and Getter
    public int getSaleId() {
        return saleId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getDate() {
        return date;
    }

    //To String override
    @Override
    public String toString() {
        return "Sale Record [ID=" + saleId +
                ", BookID=" + bookId +
                ", CustomerID=" + customerId +
                ", Quantity=" + quantity +
                ", Total Price=" + totalPrice +
                ", Date=" + date + "]";
    }
}