package model;

//This class represents the Book entity for our inventory.
public class Book {

    // Private fields to encapsulate the book's data
    private int bookId;
    private String title;
    private String author;
    private String category;
    private double price;
    private int quantity;

    //Constructor
    public Book(int bookId, String title, String author, String category, double price, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    //Setters and Getters
    public int getBookId() { 
        return bookId; 
    }
    
    public void setBookId(int bookId) { 
        this.bookId = bookId; 
    }

    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getAuthor() { 
        return author; 
    }
    
    public void setAuthor(String author) { 
        this.author = author; 
    }

    public String getCategory() { 
        return category; 
    }
    
    public void setCategory(String category) { 
        this.category = category; 
    }

    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }

    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }

    //Usage of toString to print the information.
    @Override
    public String toString() {
        return "ID: " + bookId + " | Title: " + title + " | Author: " + author + 
               " | Category: " + category + " | Price: " + price + " | Stock: " + quantity;
    }
}