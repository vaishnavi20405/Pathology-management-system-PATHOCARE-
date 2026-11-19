package patho;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Don't create new session if expired
        HttpSession session = request.getSession(false);

        // ✅ Check session exists & patientId exists
        if (session == null || session.getAttribute("patientId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ✅ Get patientId as Integer safely
        int patientId = (Integer) session.getAttribute("patientId");

        // ✅ Get updated values from form
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "UPDATE patient SET name=?, age=?, gender=?, contact=?, email=?, password=? WHERE patient_id=?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, gender);
            ps.setString(4, contact);
            ps.setString(5, email);
            ps.setString(6, password);
            ps.setInt(7, patientId);  // ✅ Use integer, not string

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                session.setAttribute("updateMsg", "Profile updated successfully!");
            } else {
                session.setAttribute("updateMsg", "No changes were made!");
            }

            response.sendRedirect("Patient/editProfile.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("updateMsg", "❌ Something went wrong while updating!");
            response.sendRedirect("Patient/editProfile.jsp");

        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
