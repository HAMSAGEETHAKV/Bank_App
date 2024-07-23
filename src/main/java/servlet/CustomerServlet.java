package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Customer;
import model.Transaction;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;

    public void init() {
        customerDAO = new CustomerDAO();
        transactionDAO = new TransactionDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("login")) {
            loginCustomer(request, response);
        } else if (action.equals("setPassword")) {
            setPassword(request, response);
        } else if (action.equals("deposit")) {
            deposit(request, response);
        } else if (action.equals("withdraw")) {
            withdraw(request, response);
        } else if (action.equals("closeAccount")) {
            closeAccount(request, response);
        } else if (action.equals("registerCustomer")) {
            registerCustomer(request, response);
        }
    }

    private void loginCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String password = request.getParameter("password");
        Customer customer = customerDAO.getCustomerByAccountNo(accountNo);
        if (customer != null && customer.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            response.sendRedirect("customer_dashboard.jsp");
        } else {
            request.setAttribute("error", "Invalid account number or password");
            request.getRequestDispatcher("customer_login.jsp").forward(request, response);
        }
    }

    private void setPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String newPassword = request.getParameter("newPassword");
        Customer customer = customerDAO.getCustomerByAccountNo(accountNo);
        if (customer != null) {
            customer.setPassword(newPassword);
            customerDAO.updateCustomer(customer);
            request.setAttribute("message", "Password updated successfully");
            request.getRequestDispatcher("customer_login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to update password");
            request.getRequestDispatcher("set_password.jsp").forward(request, response);
        }
    }

    private void deposit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        double amount = Double.parseDouble(request.getParameter("amount"));
        if (customer != null && amount > 0) {
            customer.setBalance(customer.getBalance() + amount);
            Transaction transaction = new Transaction();
            transaction.setCustomerId(customer.getCustomerId());
            transaction.setAmount(amount);
            transaction.setTransactionType("Deposit");
            if (transactionDAO.addTransaction(transaction)) {
                customerDAO.updateCustomer(customer);
                request.setAttribute("message", "Deposit successful");
            } else {
                request.setAttribute("error", "Failed to record transaction");
            }
            request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Invalid amount");
            request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
        }
    }

    private void withdraw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        double amount = Double.parseDouble(request.getParameter("amount"));
        if (customer != null && amount > 0 && customer.getBalance() >= amount) {
            customer.setBalance(customer.getBalance() - amount);
            Transaction transaction = new Transaction();
            transaction.setCustomerId(customer.getCustomerId());
            transaction.setAmount(amount);
            transaction.setTransactionType("Withdraw");
            if (transactionDAO.addTransaction(transaction)) {
                customerDAO.updateCustomer(customer);
                request.setAttribute("message", "Withdrawal successful");
            } else {
                request.setAttribute("error", "Failed to record transaction");
            }
            request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Insufficient balance or invalid amount");
            request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
        }
    }

    private void closeAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null && customer.getBalance() == 0) {
            customerDAO.deleteCustomer(customer.getCustomerId());
            session.invalidate();
            request.setAttribute("message", "Account closed successfully");
            request.getRequestDispatcher("customer_login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Account balance must be zero to close the account");
            request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
        }
    }

    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String emailId = request.getParameter("emailId");
        String accountType = request.getParameter("accountType");
        double initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        Date dob = Date.valueOf(request.getParameter("dob"));
        String idProof = request.getParameter("idProof");

        // Generate account number and temporary password
        String accountNo = UUID.randomUUID().toString();
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        // Create new customer
        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setAddress(address);
        customer.setMobileNo(mobileNo);
        customer.setEmailId(emailId);
        customer.setAccountType(accountType);
        customer.setBalance(initialBalance);
        customer.setDob(dob);
        customer.setIdProof(idProof);
        customer.setAccountNo(accountNo);
        customer.setTempPassword(tempPassword);
        customer.setPassword(tempPassword); // Initial password is the same as the temporary password

        // Save customer to the database
        if (customerDAO.addCustomer(customer)) {
            request.setAttribute("message", "Customer registered successfully. Account No: " + accountNo + " Temporary Password: " + tempPassword);
        } else {
            request.setAttribute("error", "Failed to register customer");
        }
        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
    }
}