package com.registration;

import com.registration.configuration.DatabaseConfig;
import com.registration.models.AppUser;
import com.registration.service.ValidationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private DatabaseConfig databaseConfig = new DatabaseConfig();

    private ValidationService validationService = new ValidationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AppUser user = new AppUser(name, email, password);

        boolean check = false;
        try {
            check = validationService.validate(user);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (!check) {
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            Connection conn = databaseConfig.getConnection();

            String sql = "INSERT INTO AppUser (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            request.setAttribute("name", name);

            request.getRequestDispatcher("confirmation.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error saving participant data", e);
        }
    }
}
