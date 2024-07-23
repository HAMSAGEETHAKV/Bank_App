<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Customer" %>
<%@ page import="model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("customer_login.jsp");
        return;
    }

    Object transactionsObject = request.getAttribute("transactions");
    List<Transaction> transactions = new ArrayList<>();
    if (transactionsObject instanceof List) {
        transactions = (List<Transaction>) transactionsObject;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
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

        h3 {
            margin-top: 30px;
        }

        p {
            font-size: 1.2em;
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

        input[type="number"] {
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

        p.message {
            text-align: center;
            font-size: 1.1em;
            margin-top: 10px;
        }

        p.error {
            text-align: center;
            font-size: 1.1em;
            margin-top: 10px;
            color: red; /* Error message color */
        }
    </style>
</head>
<body>
    <h2>Customer Dashboard</h2>
    <p>Welcome, <%= customer.getFullName() %></p>
    <p>Account No: <%= customer.getAccountNo() %></p>
    <p>Balance: ₹ <%= customer.getBalance() %></p>

    <h3>Actions</h3>
    <form action="CustomerServlet" method="post">
        <input type="hidden" name="action" value="deposit">
        Deposit Amount: <input type="number" name="amount" min="1" required><br>
        <input type="submit" value="Deposit">
    </form>

    <form action="CustomerServlet" method="post">
        <input type="hidden" name="action" value="withdraw">
        Withdraw Amount: <input type="number" name="amount" min="1" required><br>
        <input type="submit" value="Withdraw">
    </form>

    <form action="CustomerServlet" method="post">
        <input type="hidden" name="action" value="closeAccount">
        <input type="submit" value="Close Account">
    </form>

    <% if (request.getAttribute("message") != null) { %>
        <p style="color: green;"><%= request.getAttribute("message") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    

    <br>

    <form action="CustomerLoginRedirect" method="post">
        <button type="submit">LOG OUT</button>
    </form>
    
    
</body>
</html>
