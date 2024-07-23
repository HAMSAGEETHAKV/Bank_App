<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Login</title>
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
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px; /* Adjusted maximum width */
            margin: 0 auto; /* Center align */
        }

        input[type="text"],
        input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #5b2333; /* Maroon outline */
            border-radius: 4px;
            font-size: 1em;
        }

        input[type="submit"],
        button[type="submit"] {
            background-color: #5b2333; /* Maroon button */
            color: #fff;
            padding: 10px 20px;
            margin: 10px 0;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            cursor: pointer;
        }

        input[type="submit"]:hover,
        button[type="submit"]:hover {
            background-color: #451823; /* Darker maroon on hover */
        }

        p {
            text-align: center;
            font-size: 1em;
            color: red; /* Error message color */
        }
    </style>
</head>
<body>
    <h2>Customer Login</h2>
    
    <form action="CustomerServlet" method="post">
        <input type="hidden" name="action" value="login">
        Account No: <input type="text" name="accountNo" required><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" value="Login">
    </form>
    
    <form action="TransactionRetrieveServlet" method="post">
        <label for="accountNo">Account Number:</label>
        <input type="text" id="accountNo" name="accountNo" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Retrieve Transactions">
    </form>
    
    <% 
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) { 
    %>
        <p style="color: red;"><%= error %></p>
    <% } %>
    
    <br>
    <form action="CustomerLoginToMainadminpgRedirect" method="post">
        <button type="submit">Go Back</button>
    </form>
    
</body>
</html>
