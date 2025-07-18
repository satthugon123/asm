package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import utils.DBContext;

public class AccountDAO {
    
    public Account login(String accountID, String password) {
        String sql = "SELECT * FROM Account WHERE AccountID = ? AND Password = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, accountID);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getString("AccountID"),
                    rs.getString("UserName"),
                    rs.getString("Password"),
                    rs.getString("FullName"),
                    rs.getInt("Type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean register(Account account) {
        String sql = "INSERT INTO Account (AccountID, UserName, Password, FullName, Type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, account.getAccountID());
            ps.setString(2, account.getUserName());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getFullName());
            ps.setInt(5, account.getType());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                accounts.add(new Account(
                    rs.getString("AccountID"),
                    rs.getString("UserName"),
                    rs.getString("Password"),
                    rs.getString("FullName"),
                    rs.getInt("Type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    
    public boolean updateAccount(Account account) {
        String sql = "UPDATE Account SET UserName = ?, Password = ?, FullName = ?, Type = ? WHERE AccountID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setInt(4, account.getType());
            ps.setString(5, account.getAccountID());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteAccount(String accountID) {
        String sql = "DELETE FROM Account WHERE AccountID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, accountID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}