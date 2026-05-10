#  BookInventorySystem
##  Short Description
The Inventory BookShop Management System is a Java program that runs on the desktop.The software manages the activies within a bookshop, such as books, clients, vendors, purchases and sales.There are two users in the system: the Owner and the Manager. The Owner can perform all task in the system, whereas the Manager perfroms tasks related to sales only.

---


##  Purpose of Project
The objectives of the project are to simplfy the management of the book shop through organized inventory, customer, supplier, purchasing and sales records. This simplifies the management porcess and also shows how Object Oreinted Programming principles can be applied.

---

**Java + MySQL Desktop Application**  
Inventory Management System | OOP-Based Design | JDBC Connectivity

---

##  Group Members

| Name | CMS/ID | Section |
|------|--------|--------|
| Gaitry Ahuja | 023-25-0524 | B |
| Abeer Fatima | 023-25-0199 | B |

---

##  Demo Video

https://youtu.be/cgbPWaZdBJc?si=mFCQqKJ3tOSXrDlO

---

##  GitHub Repository

https://github.com/gaitryahuja/-BookInventorySystem.git

---

##  Main Functionalities

###  Owner Interface (Full Access)
- Add / Update / Delete Books
- Manage Customers
- Manage Suppliers
- View Sales Records
- View Purchase History
- Full system management access

###  Manager Interface (Limited Access)
- Create New Sales
- View Books
- View Customers
- View Suppliers
- View Sales & Purchase Details

---

## OOP Concepts Used

- Classes & Objects
- Encapsulation
- Inheritance
- Polymorphism
- Exception Handling
- JDBC Database Connectivity
- Layered Service-Based Design

---

##  Technologies Used

| Component | Technology |
|-----------|-----------|
| Language | Java (JDK 25) |
| Database | MySQL |
| Connectivity | JDBC |
| Editor | Notepad |
| Architecture | Model + Service Structure |

---

##  Project Structure

```text
BookInventorySystem_SectionB_Group2_GaitryAhuja/
│
├── bin/                           ← Compiled .class files
│
├── database/                      ← Database files / SQL scripts
│
├── lib/
│   └── mysql.jar                  ← MySQL JDBC Connector
│
├── src/
│   │
│   ├── model/
│   │   ├── Book.java
│   │   ├── Customer.java
│   │   ├── Person.java
│   │   ├── Purchases.java
│   │   ├── Sale.java
│   │   ├── Supplier.java
│   │   └── User.java
│   │
│   ├── service/
│   │   ├── BaseService.java
│   │   ├── BookService.java
│   │   ├── CustomerService.java
│   │   ├── PurchaseService.java
│   │   ├── SaleService.java
│   │   ├── SupplierService.java
│   │   └── DBConnection.java
│   │
│   └── Main.java
│
└── README.md
```

---

##  Setup Instructions
###  How to Unzip and Run the Project
To unzip the project, download the ZIP file and extract it using “Extract All…” or WinRAR/7-Zip into a proper folder (such as Desktop or a project directory). After extraction, open the folder and ensure all files are correctly placed. Make sure to set the proper file path in your IDE or terminal; otherwise, the program will not work correctly. Always run the project from the extracted folder, not directly from the ZIP file.

### 1. Prerequisites
- JDK 25
- MySQL 5.7+ / 8.x
- `mysql.jar` inside the `lib` folder
- Windows Command Prompt (CMD)

---

### 2. Database Setup

Open MySQL Workbench or MySQL CLI and run:

```sql
CREATE DATABASE bookshop_db;
USE bookshop_db;
```

Then import the SQL file from the `database/` folder.

This will create all required tables for:
- Books
- Customers
- Suppliers
- Sales
- Purchases

---
##  Login Information

 Before running the program, it is compulsory to first create the database and import the required SQL file. The application will not work properly without database setup.

After completing the database setup, run the program using the given compile and run instructions.

### Default Login Credentials

| Role | Password |
|------|-----------|
| Owner | `123` |
| Manager | `456` |

- Enter password `123` to access the Owner panel with full system access.
- Enter password `456` to access the Manager panel with limited access.
- If your MySQL database has a password set, enter your own database password in the `DBConnection.java` file.
- If your MySQL setup does not have any password, leave the password field empty.
---

### 3. Configure Database Connection

Edit the file:

```text
src/service/DBConnection.java
```

Update your database credentials:

```java
String url = "jdbc:mysql://localhost:3306/bookshop_db";
String user = "root";
```

---

### 4. Compile the Project

Open CMD in the project root folder and run:

```bash
javac -cp ".;lib/mysql.jar" -d bin src\model\*.java src\service\*.java src\Main.java
```

This command:
- Compiles all Java source files
- Stores compiled `.class` files inside the `bin` folder

---

### 5. Run the Project

```bash
java -cp ".;bin;lib/mysql.jar" Main
```

---
###  Mac / Linux Setup

For Mac or Linux users, follow these commands:

Make sure you are in the project root directory in Terminal.

#### Compile the Project:
```bash
javac -cp ".:lib/mysql.jar" -d bin src/model/*.java src/service/*.java src/Main.java

###Run the Project:

```bash
java -cp ".:bin:lib/mysql.jar" Main
```
---
 Note: On Mac/Linux, use : instead of ; in classpath paths. Also ensure MySQL is running and database connection is properly configured before running the project.
###  Important Notes

- Ensure MySQL server is running before execution
- `mysql.jar` must exist inside the `lib` folder
- Run commands from the root project directory
- JDK 25 should be added to system PATH

---

##  Application Flow

```text
Main Menu
├── [1] Owner Panel
│   ├── Book Management
│   │   ├── Add New Book
│   │   ├── Update Book Information
│   │   ├── Delete Book
│   │   └── View All Books
│   │
│   ├── Customer Management
│   │   ├── Add Customer
│   │   ├── Update Customer
│   │   ├── Delete Customer
│   │   └── View Customer Records
│   │
│   ├── Supplier Management
│   │   ├── Add Supplier
│   │   ├── Update Supplier
│   │   ├── Delete Supplier
│   │   └── View Supplier Records
│   │
│   ├── Purchase Management
│   │   ├── Add New Purchase
│   │   ├── View Purchase Records
│   │   └── Manage Stock Quantity
│   │
│   └── Sales Management
│       ├── Create New Sale
│       ├── View Sales Records
│       ├── Generate Bill
│       └── Update Book Quantity Automatically
│
└── [2] Manager Panel
    ├── Create New Sale
    ├── View Books
    ├── View Customers
    ├── View Suppliers
    ├── View Purchases
    └── View Sales Details
```

---

##  Database Schema (3NF)

```text
bookshop_db
├── Book
│   ├── book_id (PK)
│   ├── title
│   ├── author
│   ├── category
│   ├── price
│   └── quantity
│
├── Customer
│   ├── customer_id (PK)
│   ├── name
│   ├── contact
│   └── address
│
├── Supplier
│   ├── supplier_id (PK)
│   ├── name
│   ├── contact
│   └── address
│
├── Sale
│   ├── sale_id (PK)
│   ├── book_id (FK)
│   ├── customer_id (FK)
│   ├── quantity
│   ├── total_price
│   └── sale_date
│
└── Purchase
    ├── purchase_id (PK)
    ├── book_id (FK)
    ├── supplier_id (FK)
    ├── quantity
    ├── unit_cost
    ├── total_cost
    └── purchase_date
```

###  Relationships

- One book can appear in multiple sales and purchases
- One customer can make multiple purchases/sales
- One supplier can supply multiple books
- Foreign keys maintain data consistency between tables

---

##  Functionalities Implemented

### 1. Book Management

```text
AddBook()
UpdateBook()
DeleteBook()
ViewBooks()
```

Allows the Owner to manage inventory records including:
- Book title
- Author
- Category
- Price
- Quantity

---

### 2. Customer Management

```text
AddCustomer()
UpdateCustomer()
DeleteCustomer()
ViewCustomers()
```

Stores and manages customer information such as:
- Name
- Contact
- Address

---

### 3. Supplier Management

```text
AddSupplier()
UpdateSupplier()
DeleteSupplier()
ViewSuppliers()
```

Maintains supplier records for purchase management and inventory tracking.

---

### 4. Sales Management

```text
CreateSale()
ViewSales()
GenerateBill()
```

Handles customer purchases by:
- Recording sold books
- Calculating total price
- Updating available stock quantity automatically
- Storing sale history with date and customer details

---

### 5. Purchase Management

```text
AddPurchase()
ViewPurchases()
UpdateStock()
```

Manages book purchases from suppliers by:
- Recording supplier purchases
- Calculating total purchase cost
- Increasing inventory quantity automatically

---

##  Data Integrity & Validation

### 1. Foreign Key Constraints

```text
Sale.book_id → Book.book_id
Sale.customer_id → Customer.customer_id

Purchase.book_id → Book.book_id
Purchase.supplier_id → Supplier.supplier_id
```

Ensures valid relationships between tables and prevents invalid records.

---

### 2. Automatic Date Tracking

```text
sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

Automatically records transaction date and time.

---

### 3. Input Validation & Exception Handling

The system uses:
- Try-catch blocks
- JDBC exception handling
- Quantity and price validation
- Database connection error handling

to prevent crashes and invalid data entries.
