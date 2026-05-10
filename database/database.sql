-- 1. Setup Database
CREATE DATABASE IF NOT EXISTS bookshop_db;
USE bookshop_db;

-- 2. Create Tables
CREATE TABLE IF NOT EXISTS Book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100),
    category VARCHAR(50),
    price DOUBLE NOT NULL,
    quantity INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(20),
    address TEXT
);

CREATE TABLE IF NOT EXISTS Supplier (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(20),
    address TEXT
);

CREATE TABLE IF NOT EXISTS Sale (
    sale_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    customer_id INT,
    quantity INT NOT NULL,
    total_price DOUBLE NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES Book(book_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Purchase (
    purchase_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    supplier_id INT,
    quantity INT NOT NULL,
    unit_cost DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL, -- Fixed: Added column to match PurchaseService.java
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES Book(book_id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id) ON DELETE CASCADE
);

-- 3. Insert Sample Data
INSERT INTO Book (title, author, category, price, quantity) VALUES 
('Java Programming', 'Herbert Schildt', 'Education', 1200.00, 50),
('Database Systems', 'C.J. Date', 'Education', 1500.00, 30),
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 800.00, 20);

INSERT INTO Customer (name, contact, address) VALUES 
('Abeer Fatima', '0300-1234567', 'Sukkur IBA Hostel');

INSERT INTO Supplier (name, contact, address) VALUES 
('Oxford Press', '021-3344556', 'Karachi, Pakistan');

INSERT INTO Sale (book_id, customer_id, quantity, total_price) VALUES 
(1, 1, 2, 2400.00);

INSERT INTO Purchase (book_id, supplier_id, quantity, unit_cost, total_cost) VALUES 
(2, 1, 10, 1100.00, 11000.00);