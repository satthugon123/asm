<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PizzaStore - Pizzas Menu</title>
</head>
<body>
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h1>PizzaStore - Pizzas Menu</h1>
        <div>
            <c:if test="${not empty sessionScope.account}">
                Welcome, ${sessionScope.account.fullName}! 
                <c:if test="${sessionScope.account.staff}">
                    <a href="MainController?action=adminPage">Admin Page</a> |
                </c:if>
                <a href="MainController?action=logout">Log Out</a>
            </c:if>
            <c:if test="${empty sessionScope.account}">
                <a href="MainController?action=login">Log In</a> | <a href="MainController?action=register">Register</a>
            </c:if>
        </div>
    </div>
    
    <!-- Search Form -->
    <form action="MainController" method="get">
        <input type="hidden" name="action" value="searchProducts">
        <table>
            <tr>
                <td>Search:</td>
                <td><input type="text" name="keyword" value="${keyword}" placeholder="Product ID or Name"></td>
                <td>Price From:</td>
                <td><input type="number" name="priceFrom" value="${priceFrom}" step="0.01"></td>
                <td>Price To:</td>
                <td><input type="number" name="priceTo" value="${priceTo}" step="0.01"></td>
                <td><input type="submit" value="Search"></td>
                <td><a href="MainController?action=products">Show All</a></td>
            </tr>
        </table>
    </form>
    
    <h2>All Pizzas</h2>
    
    <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px;">
        <c:forEach var="product" items="${products}">
            <div style="border: 1px solid #ccc; padding: 15px; border-radius: 5px;">
                <c:if test="${not empty product.productImage}">
                    <img src="${product.productImage}" alt="${product.productName}" style="width: 100%; height: 200px; object-fit: cover;">
                </c:if>
                <h3>${product.productName}</h3>
                <p><strong>Price:</strong> $<fmt:formatNumber value="${product.unitPrice}" pattern="#,##0.00"/></p>
                <p><strong>Category:</strong> ${product.categoryID}</p>
                <p><strong>Quantity Per Unit:</strong> ${product.quantityPerUnit}</p>
                
                <c:if test="${not empty sessionScope.account}">
                    <button>Add Pizza</button>
                </c:if>
            </div>
        </c:forEach>
    </div>
    
    <c:if test="${empty products}">
        <p>No pizzas found.</p>
    </c:if>
</body>
</html>