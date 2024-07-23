package servlet;

import java.io.IOException;
import java.util.List;
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

@WebServlet("/TransactionRetrieveServlet")
public class TransactionRetrieveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;

    public void init() {
        customerDAO = new CustomerDAO();
        transactionDAO = new TransactionDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String password = request.getParameter("password");

        Customer customer = customerDAO.getCustomerByAccountNo(accountNo);

        if (customer != null && customer.getPassword().equals(password)) {
            List<Transaction> transactions = transactionDAO.getLast10Transactions(customer.getCustomerId());
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("transaction_display.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Invalid account number or password");
            request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
        }
    }
}
