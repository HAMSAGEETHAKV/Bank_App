package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import util.DBConnection;

public class CustomerDAO {
    public boolean registerCustomer(Customer customer) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Customer (full_name, address, mobile_no, email_id, account_type, balance, dob, id_proof, account_no, password, temp_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getMobileNo());
            statement.setString(4, customer.getEmailId());
            statement.setString(5, customer.getAccountType());
            statement.setDouble(6, customer.getBalance());
            statement.setDate(7, new java.sql.Date(customer.getDob().getTime()));
            statement.setString(8, customer.getIdProof());
            statement.setString(9, customer.getAccountNo());
            statement.setString(10, customer.getPassword());
            statement.setString(11, customer.getTempPassword());

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Customer getCustomerByAccountNo(String accountNo) {
        Customer customer = null;
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE account_no = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accountNo);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobile_no"));
                customer.setEmailId(rs.getString("email_id"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setBalance(rs.getDouble("balance"));
                customer.setDob(rs.getDate("dob"));
                customer.setIdProof(rs.getString("id_proof"));
                customer.setAccountNo(rs.getString("account_no"));
                customer.setPassword(rs.getString("password"));
                customer.setTempPassword(rs.getString("temp_password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean deleteCustomer(int i) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "DELETE FROM Customer WHERE account_no = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, i);

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateCustomer(Customer customer) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE Customer SET full_name = ?, address = ?, mobile_no = ?, email_id = ?, account_type = ?, balance = ?, dob = ?, id_proof = ?, password = ?, temp_password = ? WHERE account_no = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getMobileNo());
            statement.setString(4, customer.getEmailId());
            statement.setString(5, customer.getAccountType());
            statement.setDouble(6, customer.getBalance());
            statement.setDate(7, new java.sql.Date(customer.getDob().getTime()));
            statement.setString(8, customer.getIdProof());
            statement.setString(9, customer.getPassword());
            statement.setString(10, customer.getTempPassword());
            statement.setString(11, customer.getAccountNo());

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, full_name, address, mobile_no, email_id, account_type, balance, dob, id_proof, account_no, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
           
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getMobileNo());
            preparedStatement.setString(5, customer.getEmailId());
            preparedStatement.setString(6, customer.getAccountType());
            preparedStatement.setDouble(7, customer.getBalance());
            preparedStatement.setDate(8, customer.getDob());
            preparedStatement.setString(9, customer.getIdProof());
            preparedStatement.setString(10, customer.getAccountNo());
            preparedStatement.setString(11, customer.getPassword());

            int result = preparedStatement.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
