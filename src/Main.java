import model.*;
import service.*;
import java.util.Scanner;


public class Main {

    private static Scanner sc = new Scanner(System.in);
    

    private static CustomerService customerService = new CustomerService();
    private static BookService bookService = new BookService();
    private static SupplierService supplierService = new SupplierService();
    private static SaleService saleService = new SaleService();
    private static PurchaseService purchaseService = new PurchaseService();

    public static void main(String[] args) {

        // it call the method of DBConnection
        boolean isConnected = DBConnection.promptForPassword();

        if (!isConnected) {
            System.out.println("\n[!] SECURITY ERROR: Access Denied to Database.");
            System.out.println("The application will now close to prevent unauthorized usage.");
            System.exit(0); 
        }

        System.out.println("\n--- WELCOME TO THE BOOKSHOP MANAGEMENT SYSTEM ---");
        
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        boolean isOwner = false; 
        boolean loginSuccess = false;

        if (username.equalsIgnoreCase("Owner") && password.equals("123")) {
            System.out.println("\nLogin Successful!!! Role: OWNER");
            isOwner = true;
            loginSuccess = true;
        } else if (username.equalsIgnoreCase("Manager") && password.equals("456")) {
            System.out.println("\nLogin Successful!! Role: MANAGER");
            isOwner = false;
            loginSuccess = true;
        } else {
            System.out.println("\nInvalid Credentials! Access Denied.");
        }

        if (loginSuccess) {
            showMainMenu(isOwner);
        }
    }


    //it ask user for the function they want to perform
    private static void showMainMenu(boolean isOwner) {
        while (true) {
            System.out.println("\n********** MAIN MENU **********");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Customers");
            System.out.println("3. Manage Suppliers");
            System.out.println("4. Transactions (Sales/Purchases)");
            System.out.println("5. Exit");
            System.out.print("Select Option: ");
            
            int choice = getInt(); // Robust input handling to prevent crashes

            switch (choice) {
                case 1: manageBooks(isOwner); break;
                case 2: manageCustomers(isOwner); break;
                case 3: manageSuppliers(isOwner); break;
                case 4: manageTransactions(isOwner); break;
                case 5: 
                    System.out.println("Exiting System... Goodbye!"); 
                    System.exit(0);
                default: 
                    System.out.println("Invalid choice! Please select 1-5.");
            }
        }
    }


    //managing of books
    private static void manageBooks(boolean isOwner) {
        System.out.println("\n--- BOOK MANAGEMENT ---");
        System.out.println("1. View Inventory");
        System.out.println("2. Search Book by ID");
        

        if (isOwner) {
            System.out.println("3. Add New Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Update Book Details");
        }
        
        System.out.print("Option: ");
        int choice = getInt();

        if (choice == 1) {
            bookService.listAll();
        } else if (choice == 2) {
            System.out.print("Enter Book ID: ");
            Book b = bookService.searchBook(getInt());
            System.out.println(b != null ? b : "Book not found.");
        } else if (isOwner) {

            if (choice == 3) {
                System.out.print("Title: "); String t = sc.nextLine();
                // Data Validation: Title length check
                if (t.length() > 50) { 
		System.out.println("Limit Exceeded: Title too long."); 
		return; 
		}
                
                System.out.print("Author: "); 
		String a = sc.nextLine();
                
		System.out.print("Category: "); 
		String c = sc.nextLine();
                
                System.out.print("Price: "); 
                double p = getDouble();

                if (p > 10000) { 
		System.out.println("Limit Exceeded: Max Price is 10,000."); 
		return; }
                
                System.out.print("Qty: "); 
                int q = getInt();
 
                if (q > 500) { 
		System.out.println("Limit Exceeded: Max Stock is 500."); 
		return; 
		}
                
                bookService.addBook(new Book(0, t, a, c, p, q));
            } else if (choice == 4) {
                System.out.print("ID to Delete: ");
                bookService.deleteBook(getInt());
            } else if (choice == 5) {
                System.out.print("Enter ID of book to update: ");
                int id = getInt();
                Book existing = bookService.searchBook(id);
                if (existing != null) {
                    System.out.println("Current Data: " + existing);
                    System.out.print("New Title: "); String t = sc.nextLine();
                    if (t.length() > 50) { 
			System.out.println("Limit Exceeded: Title too long."); 
			return; }
                    
                    System.out.print("New Author: "); 
		    String a = sc.nextLine();

                    System.out.print("New Category: "); 

		    String c = sc.nextLine();
                    System.out.print("New Price: "); 
		    double p = getDouble();

                    if (p > 10000) { 
		    System.out.println("Limit Exceeded: Max Price is 10,000.");
		     return; 
			}
                    
                    System.out.print("New Qty: "); 
		    int q = getInt();

                    if (q > 500) { 
		    System.out.println("Limit Exceeded: Max Stock is 500.");
		    return; }
                    
                    bookService.updateBook(new Book(id, t, a, c, p, q));
                } 
			else {
                    System.out.println("Book not found.");
                }
            }
        } 
		else {
            System.out.println("Access Denied: Managers cannot modify book data.");
        }
    }


    //CUSTOMER MANAGEMENT
    private static void manageCustomers(boolean isOwner) {
        System.out.println("\n--- CUSTOMER MANAGEMENT ---");
        System.out.println("1. List All Customers");
        if (isOwner) {
            System.out.println("2. Add Customer");
        }
        System.out.print("Option: ");
        int choice = getInt();

        if (choice == 1) {
            customerService.listAll();
        } else if (isOwner && choice == 2) {
            System.out.print("Name: "); 
		String n = sc.nextLine();

            System.out.print("Contact: "); 
		String c = sc.nextLine();

            System.out.print("Address: "); 
		String a = sc.nextLine();

            customerService.addCustomer(new Customer(0, n, c, a));
        } 
		else if (!isOwner && choice == 2) {
            System.out.println("Access Denied: Managers cannot add customers.");
        }
    }


    //managing transactions
    private static void manageTransactions(boolean isOwner) {
        System.out.println("\n--- TRANSACTIONS ---");
        System.out.println("1. Sell Book (New Sale)");
        System.out.println("2. View Sales History");
        if (isOwner) {
            System.out.println("3. Purchase Stock (Restock)");
        }
        
        System.out.print("Option: ");
        int choice = getInt();

        if (choice == 1) {
            System.out.print("Book ID: ");
		int bid = getInt();
            System.out.print("Customer ID: "); 
		int cid = getInt();
            System.out.print("Qty to Sell: "); 
		int q = getInt();
            saleService.processSale(bid, cid, q);
        } 
	else if (choice == 2) {
            saleService.listAll();
        } 
	
	else if (choice == 3 && isOwner) {
            System.out.print("Book ID: "); 
		int pbid = getInt();
            System.out.print("Supplier ID: "); 
		int sid = getInt();

            System.out.print("Qty: "); 
		int pq = getInt();

            System.out.print("Unit Cost: "); 
            double cost = getDouble();
            purchaseService.processPurchase(pbid, sid, pq, cost);
        } 
	else if (choice == 3 && !isOwner) {
            System.out.println("Access Denied.");
        }
    }


    //SUPPLIER MANAGEMENT
    private static void manageSuppliers(boolean isOwner) {
        System.out.println("\n--- SUPPLIER MANAGEMENT ---");
        System.out.println("1. List All Suppliers");
        if (isOwner) {
            System.out.println("2. Add Supplier");
        }
        System.out.print("Option: ");
        int choice = getInt();

        if (choice == 1) {
            supplierService.listAll();
        } 
	
	else if (isOwner && choice == 2) {
            System.out.print("Name: "); 
		String n = sc.nextLine();
            System.out.print("Contact: "); 
		String c = sc.nextLine();
            System.out.print("Address: "); 
		String a = sc.nextLine();
            supplierService.addSupplier(new Supplier(0, n, c, a));
        }
 
	else if (!isOwner && choice == 2) {
            System.out.println("Access Denied.");
        }
    }

    //Input Validation
    private static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Error: Numeric value required. Please try again: ");
            }
        }
    }

    private static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Error: Invalid decimal format. Please try again: ");
            }
        }
    }
}