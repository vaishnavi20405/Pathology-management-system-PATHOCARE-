package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");

            String sql = "INSERT INTO contact (name, email, phone, message) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, message);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("contact.jsp?success=1");
            } else {
                response.sendRedirect("contact.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("contact.jsp?error=1");
        } finally {
            try { if (ps != null) ps.close(); if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }
}
