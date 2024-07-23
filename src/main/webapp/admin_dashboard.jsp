<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
    /* General styles */
    body {
        background-color: #ffe5d9; /* Peach background */
        color: #5b2333; /* Maroon text */
        font-family: 'Helvetica Neue', Arial, sans-serif; /* Attractive font */
        margin: 0;
        padding: 20px;
    }

    h2 {
        text-align: center;
        font-size: 2em;
        margin-bottom: 20px;
    }

    /* Form styles */
    form {
        background-color: #fff;
        border: 1px solid #5b2333; /* Maroon outline */
        padding: 25px; /* Adjusted padding */
        margin-bottom: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        max-width: 450px; /* Adjusted maximum width */
        margin: 0 auto; /* Center align */
    }

    input[type="text"],
    input[type="email"],
    input[type="number"],
    input[type="date"],
    select {
        width: calc(100% - 20px);
        padding: 12px; /* Adjusted padding */
        margin: 10px 0; /* Adjusted margin */
        border: 1px solid #5b2333; /* Maroon outline */
        border-radius: 6px;
        font-size: 1.1em; /* Slightly larger font size */
    }

    input[type="submit"],
    button[type="submit"] {
        background-color: #5b2333; /* Maroon button */
        color: #fff;
        padding: 12px 24px; /* Adjusted padding */
        margin: 10px 0;
        border: none;
        border-radius: 6px;
        font-size: 1.1em; /* Slightly larger font size */
        cursor: pointer;
    }

    input[type="submit"]:hover,
    button[type="submit"]:hover {
        background-color: #451823; /* Darker maroon on hover */
    }

    p {
        text-align: center;
        font-size: 1em;
    }
</style>
    
    
</head>
<body>
    <h2>Admin Dashboard</h2>
    
    <form action="AdminServlet" method="post">
        <input type="hidden" name="action" value="registerCustomer">
        Full Name: <input type="text" name="fullName" required><br>
        Address: <input type="text" name="address"><br>
        Mobile No: <input type="text" name="mobileNo"><br>
        Email ID: <input type="email" name="emailId" required><br>
        Account Type: 
        <select name="accountType">
            <option value="Saving">Saving</option>
            <option value="Current">Current</option>
        </select><br>
        Initial Balance: <input type="number" name="balance" min="1000" required><br>
        Date of Birth: <input type="date" name="dob" required><br>
        ID Proof: <input type="text" name="idProof"><br>
        <input type="submit" value="Register Customer">
    </form>
    
    <% 
        String message = (String) request.getAttribute("message");
        if (message != null && !message.isEmpty()) { 
    %>
        <p style="color: green;"><%= message %></p>
    <% } %>
    
    <% 
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) { 
    %>
        <p style="color: red;"><%= error %></p>
    <% } %>
    
    
    <form action="AdminLogoutRedirect" method="post">
        <button type="submit">LOG OUT</button>
    </form>
</body>
</html>
