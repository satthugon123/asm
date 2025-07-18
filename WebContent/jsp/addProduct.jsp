<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PizzaStore - Add Product</title>
</head>
<body>
    <h1>PizzaStore - Add New Product</h1>
    
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="addProduct">
        
        <table>
            <tr>
                <td>Product ID:</td>
                <td><input type="text" name="productID" required></td>
            </tr>
            <tr>
                <td>Product Name:</td>
                <td><input type="text" name="productName" required></td>
            </tr>
            <tr>
                <td>Supplier ID:</td>
                <td><input type="text" name="supplierID"></td>
            </tr>
            <tr>
                <td>Category ID:</td>
                <td>
                    <select name="categoryID">
                        <option value="Standard">Standard</option>
                        <option value="Specialties">Specialties</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Quantity Per Unit:</td>
                <td><input type="number" name="quantity" required></td>
            </tr>
            <tr>
                <td>Unit Price:</td>
                <td><input type="number" name="price" step="0.01" required></td>
            </tr>
            <tr>
                <td>Product Image (URL):</td>
                <td><input type="text" name="productImage"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add Product">
                    <a href="MainController?action=adminPage">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>