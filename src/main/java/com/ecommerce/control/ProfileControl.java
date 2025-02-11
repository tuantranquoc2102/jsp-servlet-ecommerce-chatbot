package com.ecommerce.control;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.entity.Account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ProfileControl", value = "/profile-page")
@MultipartConfig
public class ProfileControl extends HttpServlet {
    // Call DAO class to access with the database.
    AccountDao accountDao = new AccountDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile-page.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        int accountId = account.getId();
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Set default profile image for account.
        Part part = request.getPart("profile-image");
        InputStream inputStream = (part != null && part.getSize() > 0) ? part.getInputStream() : null;

        // 🚀 Validate dữ liệu nhập vào
        List<String> errors = new ArrayList<>();

        if (firstName.isEmpty()) {
            errors.add("First name cannot be empty.");
        }
        if (lastName.isEmpty()) {
            errors.add("Last name cannot be empty.");
        }
        if (address.isEmpty()) {
            errors.add("Address cannot be empty.");
        }
        if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.add("Invalid email format.");
        }
        if (phone.isEmpty() || !phone.matches("^\\d{10,12}$")) {
            errors.add("Phone number must contain 10-12 digits.");
        }

        // TH có lỗi xảy ra
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("address", address);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("profile-page.jsp").forward(request, response);
            return;
        }

        // 🚀 Thực hiện cập nhật thông tin
        boolean updated = accountDao.editProfileInformation(accountId, firstName, lastName, address, email, phone, inputStream);

        if (updated) {
            request.setAttribute("successMessage", "Profile updated successfully!");
            // Cập nhật lại session với dữ liệu mới
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setAddress(address);
            account.setEmail(email);
            account.setPhone(phone);

            // 🚀 Lấy lại ảnh từ database
            String base64Image = "";
            try {
                base64Image = accountDao.getProfileImage(accountId);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            account.setBase64Image(base64Image);

            // Cập nhật lại session
            session.setAttribute("account", account);
        } else {
            request.setAttribute("errorMessage", "Profile update failed. Please try again.");
        }

        request.getRequestDispatcher("profile-page.jsp").forward(request, response);
    }
}