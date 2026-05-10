package model;

import java.sql.Date;

//It tracks which book was bought, from whom, and the associated costs.
public class Purchase {

    private int purchaseId;
    private int bookId;
    private int supplierId;
    private int quantity;
    private double totalCost;
    private Date date;

    //Constructor
    public Purchase(int purchaseId, int bookId, int supplierId, int quantity, double totalCost, Date date) {
        this.purchaseId = purchaseId;
        this.bookId = bookId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.date = date;
    }

    //Getters
    public int getPurchaseId() { 
        return purchaseId; 
    }

    public int getBookId() { 
        return bookId; 
    }

    public int getSupplierId() { 
        return supplierId; 
    }

    public int getQuantity() { 
        return quantity; 
    }

    public double getTotalCost() { 
        return totalCost; 
    }

    public Date getDate() { 
        return date; 
    }

    //Formats purchase details
    @Override
    public String toString() {
        return "Purchase [ID=" + purchaseId + ", BookID=" + bookId + ", SupplierID=" + supplierId + 
               ", Qty=" + quantity + ", Total Cost=" + totalCost + ", Date=" + date + "]";
    }
}