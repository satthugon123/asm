<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PizzaStore - Login</title>
</head>
<body>
    <h1>PizzaStore - Please log in here</h1>
    <p>Enter your details below</p>
    
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    
    <c:if test="${not empty success}">
        <div style="color: green;">${success}</div>
    </c:if>
    
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="login">
        
        <table>
            <tr>
                <td>User Name:</td>
                <td><input type="text" name="accountID" required></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Log In">
                </td>
            </tr>
        </table>
    </form>
    
    <p><a href="MainController?action=register">Register</a> | <a href="MainController?action=products">View Pizzas</a></p>
</body>
</html>