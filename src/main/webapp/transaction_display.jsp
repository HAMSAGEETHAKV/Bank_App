<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Transaction" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Last 10 Transactions</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #5b2333; /* Maroon border */
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #5b2333; /* Maroon background */
            color: #fff; /* White text */
        }

        td {
            background-color: #fff; /* White background */
        }

        /* Button styles */
        button[type="submit"] {
            background-color: #5b2333; /* Maroon button */
            color: #fff;
            padding: 10px 20px;
            margin-top: 20px;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: #451823; /* Darker maroon on hover */
        }
    </style>
</head>
<body>
    <h2>Last 10 Transactions</h2>

    <table>
        <thead>
            <tr>
                <th>Transaction ID</th>
                <th>Customer ID</th>
                <th>Amount</th>
                <th>Type</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <% List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
               if (transactions != null && !transactions.isEmpty()) {
                   for (Transaction transaction : transactions) { %>
                       <tr>
                           <td><%= transaction.getTransactionId() %></td>
                           <td><%= transaction.getCustomerId() %></td>
                           <td>₹ <%= transaction.getAmount() %></td>
                           <td><%= transaction.getTransactionType() %></td>
                           <td><%= transaction.getTransactionDate() %></td>
                       </tr>
                   <% }
               } else { %>
                   <tr>
                       <td colspan="5">No transactions found.</td>
                   </tr>
               <% } %>
        </tbody>
    </table>
    
    <br>
    <form action="CustomerLoginRedirect" method="post">
        <button type="submit">LOG OUT</button>
    </form>

</body>
</html>
