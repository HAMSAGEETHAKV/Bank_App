
package servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import dao.CustomerDAO;
import model.Admin;
import model.Customer;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AdminDAO adminDAO;
    private CustomerDAO customerDAO;

    public void init() {
        adminDAO = new AdminDAO();
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("login")) {
            loginAdmin(request, response);
        } else if (action.equals("registerCustomer")) {
            registerCustomer(request, response);
        }
    }

    private void loginAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = adminDAO.getAdmin(username, password);
        if(username.equals("hamsa") && password.equals("123")) {
            response.sendRedirect("admin_dashboard.jsp");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("admin_login.jsp").forward(request, response);
        }
    }

    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String emailId = request.getParameter("emailId");
        String accountType = request.getParameter("accountType");
        double balance = Double.parseDouble(request.getParameter("balance"));
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("idProof");

        String accountNo = UUID.randomUUID().toString().substring(0, 8);
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setAddress(address);
        customer.setMobileNo(mobileNo);
        customer.setEmailId(emailId);
        customer.setAccountType(accountType);
        customer.setBalance(balance);
        customer.setDob(java.sql.Date.valueOf(dob));
        customer.setIdProof(idProof);
        customer.setAccountNo(accountNo);
        customer.setTempPassword(tempPassword);
        customer.setPassword(tempPassword); // temp password for initial setup

        if (customerDAO.registerCustomer(customer)) {
            request.setAttribute("message", "Customer registered successfully with Account No: " + accountNo + " and Temporary Password: " + tempPassword);
        } else {
            request.setAttribute("error", "Failed to register customer");
        }
        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
    }
}

