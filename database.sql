-- Create Database
CREATE DATABASE PizzaStore;
GO

USE PizzaStore;
GO

-- Create Categories table
CREATE TABLE Categories (
    CategoryID NVARCHAR(50) PRIMARY KEY,
    CategoryName NVARCHAR(100),
    Description NTEXT
);

-- Create Suppliers table
CREATE TABLE Suppliers (
    SupplierID NVARCHAR(50) PRIMARY KEY,
    CompanyName NVARCHAR(100),
    Address NVARCHAR(200),
    Phone NVARCHAR(20)
);

-- Create Account table
CREATE TABLE Account (
    AccountID NVARCHAR(50) PRIMARY KEY,
    UserName NVARCHAR(100) NOT NULL,
    Password NVARCHAR(100) NOT NULL,
    FullName NVARCHAR(100) NOT NULL,
    Type INT NOT NULL -- 1: Staff, 2: Customer
);

-- Create Products table
CREATE TABLE Products (
    ProductID NVARCHAR(50) PRIMARY KEY,
    ProductName NVARCHAR(100) NOT NULL,
    SupplierID NVARCHAR(50),
    CategoryID NVARCHAR(50),
    QuantityPerUnit INT,
    UnitPrice DECIMAL(10,2),
    ProductImage NVARCHAR(500),
    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- Create Customers table (extends Account for customers)
CREATE TABLE Customers (
    CustomerID NVARCHAR(50) PRIMARY KEY,
    Password NVARCHAR(100),
    ContactName NVARCHAR(100),
    Address NVARCHAR(200),
    Phone NVARCHAR(20),
    FOREIGN KEY (CustomerID) REFERENCES Account(AccountID)
);

-- Create Orders table
CREATE TABLE Orders (
    OrderID NVARCHAR(50) PRIMARY KEY,
    CustomerID NVARCHAR(50),
    OrderDate DATETIME,
    RequiredDate DATETIME,
    ShippedDate DATETIME,
    Freight DECIMAL(10,2),
    ShipAddress NVARCHAR(200),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

-- Create Order Details table
CREATE TABLE OrderDetails (
    OrderID NVARCHAR(50),
    ProductID NVARCHAR(50),
    UnitPrice DECIMAL(10,2),
    Quantity INT,
    PRIMARY KEY (OrderID, ProductID),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Insert sample data
INSERT INTO Categories VALUES 
('Standard', 'Standard', 'Standard pizzas'),
('Specialties', 'Specialties', 'Specialty pizzas');

INSERT INTO Suppliers VALUES 
('SUP001', 'Pizza Supplier Inc', '123 Main St', '555-0001');

INSERT INTO Account VALUES 
('admin', 'admin', 'admin', 'Administrator', 1),
('customer1', 'john', '123', 'John Doe', 2);

INSERT INTO Products VALUES 
('P001', 'Capricciosa', 'SUP001', 'Standard', 1, 70.00, 'Pizza_capricciosa.jpg'),
('P002', 'Hawaii', 'SUP001', 'Standard', 1, 75.00, 'Hawaiian_pizza_1.jpg'),
('P003', 'Margarita', 'SUP001', 'Standard', 1, 65.00, 'Eat_It-na_pizza-margherita_san2005_sml.jpg'),
('P004', 'Pescatore', 'SUP001', 'Standard', 1, 80.00, 'dsc_0231.jpg'),
('P005', 'Barcelona', 'SUP001', 'Specialties', 1, 70.00, 'pizza-jamon-dulce-y-champinones.jpg');