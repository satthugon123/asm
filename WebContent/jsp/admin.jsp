<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PizzaStore - Admin Page</title>
</head>
<body>
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h1>PizzaStore - Admin Page</h1>
        <div>
            Welcome, ${sessionScope.account.fullName}! 
            <a href="MainController?action=products">View Products</a> |
            <a href="MainController?action=logout">Log Out</a>
        </div>
    </div>
    
    <p><a href="MainController?action=addProduct">Create New</a></p>
    
    <h2>Pizzas</h2>
    
    <table border="1" style="width: 100%; border-collapse: collapse;">
        <thead>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Image/URL</th>
                <th>IsPizzaOfTheWeek</th>
                <th>Category</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.productName}</td>
                    <td>$<fmt:formatNumber value="${product.unitPrice}" pattern="#,##0.00"/></td>
                    <td>${product.quantityPerUnit}</td>
                    <td>
                        <c:if test="${not empty product.productImage}">
                            <img src="${product.productImage}" alt="${product.productName}" style="width: 50px; height: 50px;">
                        </c:if>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${product.categoryID == 'Specialties'}">
                                <input type="checkbox" checked disabled>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" disabled>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${product.categoryID}</td>
                    <td>
                        <a href="MainController?action=editProduct&productID=${product.productID}">Edit</a> |
                        <a href="MainController?action=deleteProduct&productID=${product.productID}" 
                           onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <c:if test="${empty products}">
        <p>No products found.</p>
    </c:if>
</body>
</html>