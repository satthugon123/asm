package model;

public class Product {
    private String productID;
    private String productName;
    private String supplierID;
    private String categoryID;
    private int quantityPerUnit;
    private double unitPrice;
    private String productImage;
    
    public Product() {}
    
    public Product(String productID, String productName, String supplierID, 
                  String categoryID, int quantityPerUnit, double unitPrice, String productImage) {
        this.productID = productID;
        this.productName = productName;
        this.supplierID = supplierID;
        this.categoryID = categoryID;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.productImage = productImage;
    }
    
    // Getters and Setters
    public String getProductID() { return productID; }
    public void setProductID(String productID) { this.productID = productID; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getSupplierID() { return supplierID; }
    public void setSupplierID(String supplierID) { this.supplierID = supplierID; }
    
    public String getCategoryID() { return categoryID; }
    public void setCategoryID(String categoryID) { this.categoryID = categoryID; }
    
    public int getQuantityPerUnit() { return quantityPerUnit; }
    public void setQuantityPerUnit(int quantityPerUnit) { this.quantityPerUnit = quantityPerUnit; }
    
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    
    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
}