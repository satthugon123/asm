<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PizzaStore - Register</title>
</head>
<body>
    <h1>PizzaStore - Register</h1>
    
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="register">
        
        <table>
            <tr>
                <td>Account ID:</td>
                <td><input type="text" name="accountID" required></td>
            </tr>
            <tr>
                <td>User Name:</td>
                <td><input type="text" name="userName" required></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td>Full Name:</td>
                <td><input type="text" name="fullName" required></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Register">
                    <a href="MainController?action=login">Back to Login</a>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>