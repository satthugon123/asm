package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dao.ProductDAO;
import model.Account;
import model.Product;

@WebServlet("/MainController")
public class MainController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private AccountDAO accountDAO = new AccountDAO();
    private ProductDAO productDAO = new ProductDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "home";
        
        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "logout":
                handleLogout(request, response);
                break;
            case "register":
                handleRegister(request, response);
                break;
            case "products":
                handleProducts(request, response);
                break;
            case "searchProducts":
                handleSearchProducts(request, response);
                break;
            case "addProduct":
                handleAddProduct(request, response);
                break;
            case "editProduct":
                handleEditProduct(request, response);
                break;
            case "updateProduct":
                handleUpdateProduct(request, response);
                break;
            case "deleteProduct":
                handleDeleteProduct(request, response);
                break;
            case "adminPage":
                handleAdminPage(request, response);
                break;
            default:
                handleHome(request, response);
                break;
        }
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountID = request.getParameter("accountID");
        String password = request.getParameter("password");
        
        if (accountID != null && password != null) {
            Account account = accountDAO.login(accountID, password);
            if (account != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                
                if (account.isStaff()) {
                    response.sendRedirect("MainController?action=adminPage");
                } else {
                    response.sendRedirect("MainController?action=products");
                }
                return;
            } else {
                request.setAttribute("error", "Invalid username or password");
            }
        }
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }
    
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("MainController");
    }
    
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountID = request.getParameter("accountID");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        
        if (accountID != null && userName != null && password != null && fullName != null) {
            Account account = new Account(accountID, userName, password, fullName, 2); // Default customer
            if (accountDAO.register(account)) {
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("error", "Registration failed. Account ID may already exist.");
            }
        }
        request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
    }
    
    private void handleProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("jsp/products.jsp").forward(request, response);
    }
    
    private void handleSearchProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String priceFromStr = request.getParameter("priceFrom");
        String priceToStr = request.getParameter("priceTo");
        
        Double priceFrom = null;
        Double priceTo = null;
        
        if (priceFromStr != null && !priceFromStr.trim().isEmpty()) {
            try {
                priceFrom = Double.parseDouble(priceFromStr);
            } catch (NumberFormatException e) {}
        }
        
        if (priceToStr != null && !priceToStr.trim().isEmpty()) {
            try {
                priceTo = Double.parseDouble(priceToStr);
            } catch (NumberFormatException e) {}
        }
        
        List<Product> products = productDAO.searchProducts(keyword, priceFrom, priceTo);
        request.setAttribute("products", products);
        request.setAttribute("keyword", keyword);
        request.setAttribute("priceFrom", priceFromStr);
        request.setAttribute("priceTo", priceToStr);
        request.getRequestDispatcher("jsp/products.jsp").forward(request, response);
    }
    
    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is staff
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null || !account.isStaff()) {
            response.sendRedirect("MainController?action=login");
            return;
        }
        
        String productID = request.getParameter("productID");
        if (productID != null) {
            String productName = request.getParameter("productName");
            String supplierID = request.getParameter("supplierID");
            String categoryID = request.getParameter("categoryID");
            String quantityStr = request.getParameter("quantity");
            String priceStr = request.getParameter("price");
            String productImage = request.getParameter("productImage");
            
            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);
                
                Product product = new Product(productID, productName, supplierID, categoryID, quantity, price, productImage);
                if (productDAO.addProduct(product)) {
                    response.sendRedirect("MainController?action=adminPage");
                    return;
                } else {
                    request.setAttribute("error", "Failed to add product");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid number format");
            }
        }
        request.getRequestDispatcher("jsp/addProduct.jsp").forward(request, response);
    }
    
    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        Product product = productDAO.getProductById(productID);
        request.setAttribute("product", product);
        request.getRequestDispatcher("jsp/editProduct.jsp").forward(request, response);
    }
    
    private void handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        String productName = request.getParameter("productName");
        String supplierID = request.getParameter("supplierID");
        String categoryID = request.getParameter("categoryID");
        String quantityStr = request.getParameter("quantity");
        String priceStr = request.getParameter("price");
        String productImage = request.getParameter("productImage");
        
        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            
            Product product = new Product(productID, productName, supplierID, categoryID, quantity, price, productImage);
            if (productDAO.updateProduct(product)) {
                response.sendRedirect("MainController?action=adminPage");
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
        }
        
        request.setAttribute("error", "Failed to update product");
        handleEditProduct(request, response);
    }
    
    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        productDAO.deleteProduct(productID);
        response.sendRedirect("MainController?action=adminPage");
    }
    
    private void handleAdminPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null || !account.isStaff()) {
            response.sendRedirect("MainController?action=login");
            return;
        }
        
        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("jsp/admin.jsp").forward(request, response);
    }
    
    private void handleHome(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account != null) {
            if (account.isStaff()) {
                response.sendRedirect("MainController?action=adminPage");
            } else {
                response.sendRedirect("MainController?action=products");
            }
        } else {
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }
}