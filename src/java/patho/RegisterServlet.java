package patho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // DB connection
            con = DBConnection.getConnection();

            String query = "INSERT INTO patient(name, age, gender, contact, email, password) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(age));
            ps.setString(3, gender);
            ps.setString(4, contact);
            ps.setString(5, email);
            ps.setString(6, password);

            int i = ps.executeUpdate();

            if (i > 0) {
                // ✅ Get the generated patient_id
                rs = ps.getGeneratedKeys();
                int patientId = 0;
                if (rs.next()) {
                    patientId = rs.getInt(1);
                }

                // ✅ Auto login after successful registration
                HttpSession session = request.getSession();
                session.setAttribute("patientId", patientId);
                session.setAttribute("patientName", name);
                session.setAttribute("patientEmail", email);
                session.setAttribute("patientContact", contact);

                // ✅ Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/Patient/patientDashboard.jsp");
            } else {
                out.println("<script>alert('Registration Failed. Try again!');window.location='register.jsp';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "');window.location='register.jsp';</script>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
