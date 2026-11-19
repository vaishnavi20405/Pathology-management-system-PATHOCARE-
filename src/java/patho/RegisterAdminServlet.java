package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

/**
 * Handles admin registration requests.
 */
@WebServlet("/RegisterAdminServlet")
public class RegisterAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ✅ Get form data
        String adminId = request.getParameter("adminId");
        String name = request.getParameter("name");
        String branch = request.getParameter("branch");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // ✅ Use working DB connection helper
            con = DBConnection.getConnection();

            // ✅ Insert new admin record
            String query = "INSERT INTO admin (admin_id, name, branch, phone_no, email, password) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, adminId);
            ps.setString(2, name);
            ps.setString(3, branch);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setString(6, password);

            int result = ps.executeUpdate();

            if (result > 0) {
                // ✅ Create session
                HttpSession session = request.getSession();
                session.setAttribute("adminName", name);
                session.setAttribute("adminEmail", email);
                session.setAttribute("adminId", adminId);

                // ✅ Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/Admin/adminDashboard.jsp");
            } else {
                out.println("<script>alert('Registration failed. Please try again.'); window.location='/Admin/adminRegister.jsp';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='/Admin/adminRegister.jsp';</script>");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }
}
