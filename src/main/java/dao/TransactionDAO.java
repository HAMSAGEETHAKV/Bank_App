package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import util.DBConnection;

public class TransactionDAO {
	public boolean addTransaction(Transaction transaction) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Transaction (customer_id, amount, transaction_type) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, transaction.getCustomerId());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getTransactionType());

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	public List<Transaction> getLast10Transactions(int customerId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Transaction WHERE customer_id = ? ORDER BY transaction_date DESC LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setCustomerId(rs.getInt("customer_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}
