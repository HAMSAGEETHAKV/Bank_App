<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Transaction" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Last 10 Transactions</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 8px;
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

</body>
</html>
