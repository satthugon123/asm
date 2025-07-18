package model;

public class Account {
    private String accountID;
    private String userName;
    private String password;
    private String fullName;
    private int type; // 1: Staff, 2: Customer
    
    public Account() {}
    
    public Account(String accountID, String userName, String password, String fullName, int type) {
        this.accountID = accountID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.type = type;
    }
    
    // Getters and Setters
    public String getAccountID() { return accountID; }
    public void setAccountID(String accountID) { this.accountID = accountID; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    
    public boolean isStaff() { return type == 1; }
    public boolean isCustomer() { return type == 2; }
}