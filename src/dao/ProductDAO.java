package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import utils.DBContext;

public class ProductDAO {
    
    private List<Product> getMockProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("P001", "Capricciosa", "SUP001", "Standard", 1, 70.00, "Pizza_capricciosa.jpg"));
        products.add(new Product("P002", "Hawaii", "SUP001", "Standard", 1, 75.00, "Hawaiian_pizza_1.jpg"));
        products.add(new Product("P003", "Margarita", "SUP001", "Standard", 1, 65.00, "Eat_It-na_pizza-margherita_san2005_sml.jpg"));
        products.add(new Product("P004", "Pescatore", "SUP001", "Standard", 1, 80.00, "dsc_0231.jpg"));
        products.add(new Product("P005", "Barcelona", "SUP001", "Specialties", 1, 70.00, "pizza-jamon-dulce-y-champinones.jpg"));
        return products;
    }
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Products";
            
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    products.add(new Product(
                        rs.getString("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("SupplierID"),
                        rs.getString("CategoryID"),
                        rs.getInt("QuantityPerUnit"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("ProductImage")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error in getAllProducts, using mock data: " + e.getMessage());
            return getMockProducts();
        }
        return products;
    }
    
    public Product getProductById(String productID) {
        try {
            String sql = "SELECT * FROM Products WHERE ProductID = ?";
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, productID);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    return new Product(
                        rs.getString("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("SupplierID"),
                        rs.getString("CategoryID"),
                        rs.getInt("QuantityPerUnit"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("ProductImage")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error in getProductById, using mock data: " + e.getMessage());
            // Search in mock data
            for (Product product : getMockProducts()) {
                if (product.getProductID().equals(productID)) {
                    return product;
                }
            }
        }
        return null;
    }
    
    public List<Product> searchProducts(String keyword, Double priceFrom, Double priceTo) {
        List<Product> products = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM Products WHERE 1=1");
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (ProductID LIKE ? OR LOWER(ProductName) LIKE LOWER(?))");
            }
            if (priceFrom != null) {
                sql.append(" AND UnitPrice >= ?");
            }
            if (priceTo != null) {
                sql.append(" AND UnitPrice <= ?");
            }
            
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                
                int paramIndex = 1;
                if (keyword != null && !keyword.trim().isEmpty()) {
                    ps.setString(paramIndex++, "%" + keyword + "%");
                    ps.setString(paramIndex++, "%" + keyword + "%");
                }
                if (priceFrom != null) {
                    ps.setDouble(paramIndex++, priceFrom);
                }
                if (priceTo != null) {
                    ps.setDouble(paramIndex++, priceTo);
                }
                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    products.add(new Product(
                        rs.getString("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("SupplierID"),
                        rs.getString("CategoryID"),
                        rs.getInt("QuantityPerUnit"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("ProductImage")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error in searchProducts, using mock data: " + e.getMessage());
            // Search in mock data
            List<Product> mockProducts = getMockProducts();
            for (Product product : mockProducts) {
                boolean matches = true;
                
                if (keyword != null && !keyword.trim().isEmpty()) {
                    String lowerKeyword = keyword.toLowerCase();
                    if (!product.getProductID().toLowerCase().contains(lowerKeyword) &&
                        !product.getProductName().toLowerCase().contains(lowerKeyword)) {
                        matches = false;
                    }
                }
                
                if (priceFrom != null && product.getUnitPrice() < priceFrom) {
                    matches = false;
                }
                
                if (priceTo != null && product.getUnitPrice() > priceTo) {
                    matches = false;
                }
                
                if (matches) {
                    products.add(product);
                }
            }
        }
        return products;
    }
    
    public boolean addProduct(Product product) {
        try {
            String sql = "INSERT INTO Products (ProductID, ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, ProductImage) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, product.getProductID());
                ps.setString(2, product.getProductName());
                ps.setString(3, product.getSupplierID());
                ps.setString(4, product.getCategoryID());
                ps.setInt(5, product.getQuantityPerUnit());
                ps.setDouble(6, product.getUnitPrice());
                ps.setString(7, product.getProductImage());
                
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Database error in addProduct: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateProduct(Product product) {
        try {
            String sql = "UPDATE Products SET ProductName = ?, SupplierID = ?, CategoryID = ?, QuantityPerUnit = ?, UnitPrice = ?, ProductImage = ? WHERE ProductID = ?";
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, product.getProductName());
                ps.setString(2, product.getSupplierID());
                ps.setString(3, product.getCategoryID());
                ps.setInt(4, product.getQuantityPerUnit());
                ps.setDouble(5, product.getUnitPrice());
                ps.setString(6, product.getProductImage());
                ps.setString(7, product.getProductID());
                
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Database error in updateProduct: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteProduct(String productID) {
        try {
            String sql = "DELETE FROM Products WHERE ProductID = ?";
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, productID);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Database error in deleteProduct: " + e.getMessage());
            return false;
        }
    }
}