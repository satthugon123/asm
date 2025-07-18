<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PizzaStore - Edit Product</title>
</head>
<body>
    <h1>PizzaStore - Edit Product</h1>
    
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="updateProduct">
        <input type="hidden" name="productID" value="${product.productID}">
        
        <table>
            <tr>
                <td>Product ID:</td>
                <td><input type="text" value="${product.productID}" disabled></td>
            </tr>
            <tr>
                <td>Product Name:</td>
                <td><input type="text" name="productName" value="${product.productName}" required></td>
            </tr>
            <tr>
                <td>Supplier ID:</td>
                <td><input type="text" name="supplierID" value="${product.supplierID}"></td>
            </tr>
            <tr>
                <td>Category ID:</td>
                <td>
                    <select name="categoryID">
                        <option value="Standard" ${product.categoryID == 'Standard' ? 'selected' : ''}>Standard</option>
                        <option value="Specialties" ${product.categoryID == 'Specialties' ? 'selected' : ''}>Specialties</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Quantity Per Unit:</td>
                <td><input type="number" name="quantity" value="${product.quantityPerUnit}" required></td>
            </tr>
            <tr>
                <td>Unit Price:</td>
                <td><input type="number" name="price" value="${product.unitPrice}" step="0.01" required></td>
            </tr>
            <tr>
                <td>Product Image (URL):</td>
                <td><input type="text" name="productImage" value="${product.productImage}"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Update Product">
                    <a href="MainController?action=adminPage">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>