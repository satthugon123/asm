# PizzaStore Web Application

A simple Java Web Application for managing pizzas, customers, and orders using MVC2 architecture.

## Features

- **Member Management**: Login, registration, user roles (Staff/Customer)
- **Product Management**: CRUD operations for pizzas (Staff only)
- **Search Functionality**: Search products by ID, name, and price range
- **Order Management**: Basic order functionality
- **Role-based Access**: Different permissions for Staff and Customers

## Technology Stack

- **Backend**: Java Servlets, JSP, JSTL
- **Database**: Microsoft SQL Server
- **Architecture**: MVC2 Pattern
- **Build Tool**: Apache Ant

## Project Structure

```
PizzaStore/
│
├── src/
│   ├── controller/
│   │   └── MainController.java    ← Main servlet handling all requests
│   ├── model/
│   │   ├── Account.java           ← User account model
│   │   ├── Product.java           ← Product model
│   │   └── Order.java             ← Order model
│   ├── dao/
│   │   ├── AccountDAO.java        ← Account data access
│   │   └── ProductDAO.java        ← Product data access
│   └── utils/
│       └── DBContext.java         ← Database connection utility
│
├── WebContent/
│   ├── jsp/
│   │   ├── login.jsp              ← Login page
│   │   ├── register.jsp           ← Registration page
│   │   ├── products.jsp           ← Product listing/search
│   │   ├── admin.jsp              ← Admin dashboard
│   │   ├── addProduct.jsp         ← Add product form
│   │   └── editProduct.jsp        ← Edit product form
│   └── WEB-INF/
│       ├── web.xml                ← Web app configuration
│       └── lib/                   ← JAR libraries
│
├── database.sql                   ← Database creation script
├── build.xml                      ← Ant build configuration
└── README.md                      ← This file
```

## Setup Instructions

### 1. Database Setup

1. Install Microsoft SQL Server
2. Execute the `database.sql` script to create the database and tables
3. Update database connection settings in `src/utils/DBContext.java`:
   - Server URL
   - Username/Password
   - Database name

### 2. Required Libraries

Download and place in `WebContent/WEB-INF/lib/`:

- **Microsoft SQL Server JDBC Driver**: `mssql-jdbc-x.x.x.jre8.jar`
- **JSTL Library**: `jstl-1.2.jar`
- **Servlet API**: `servlet-api.jar` (for compilation only)

### 3. Build and Deploy

#### Using Apache Ant:
```bash
ant clean compile build
```

#### Manual Compilation:
```bash
# Create classes directory
mkdir -p WebContent/WEB-INF/classes

# Compile Java files
javac -cp "WebContent/WEB-INF/lib/*" -d WebContent/WEB-INF/classes src/**/*.java
```

#### Deploy to Tomcat:
1. Copy the entire `WebContent` folder to Tomcat's `webapps` directory
2. Rename it to `PizzaStore`
3. Start Tomcat server

### 4. Access the Application

- **URL**: `http://localhost:8080/PizzaStore`
- **Default Admin**: username=`admin`, password=`admin`
- **Default Customer**: username=`customer1`, password=`123`

## Usage

### For Customers:
- Browse pizzas on the main menu
- Search products by name/ID or price range
- Register new account
- View product details

### For Staff:
- All customer features plus:
- Access admin dashboard
- Add new products
- Edit existing products
- Delete products
- Manage product categories

## Database Schema

### Main Tables:
- **Account**: User accounts (Staff/Customer)
- **Products**: Pizza products
- **Categories**: Product categories
- **Suppliers**: Product suppliers
- **Orders**: Customer orders
- **OrderDetails**: Order line items

### Sample Data:
- **Pizzas**: Capricciosa, Hawaii, Margarita, Pescatore, Barcelona
- **Categories**: Standard, Specialties
- **Admin Account**: admin/admin
- **Customer Account**: customer1/123

## API Endpoints

All requests go through `MainController` servlet:

- `?action=login` - User login
- `?action=register` - User registration
- `?action=products` - View all products
- `?action=searchProducts` - Search products
- `?action=adminPage` - Admin dashboard (Staff only)
- `?action=addProduct` - Add new product (Staff only)
- `?action=editProduct&productID=X` - Edit product (Staff only)
- `?action=deleteProduct&productID=X` - Delete product (Staff only)
- `?action=logout` - User logout

## Security Features

- Session-based authentication
- Role-based access control
- SQL injection prevention using PreparedStatements
- Password validation
- Session timeout configuration

## Future Enhancements

- Password encryption
- Email verification
- Shopping cart functionality
- Order history
- Payment integration
- Advanced reporting
- CSS styling
- Input validation improvements