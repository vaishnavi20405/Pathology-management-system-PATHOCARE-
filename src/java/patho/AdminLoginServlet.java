package patho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String adminId = request.getParameter("adminId");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            // ✅ Step 1: Check if admin ID exists
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM admin WHERE admin_id = ?");
            ps1.setString(1, adminId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                out.println("<script>alert('Invalid Admin ID!'); window.location='login.jsp';</script>");
                return;
            }

            //  Check if email matches the given admin ID
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM admin WHERE admin_id = ? AND email = ?");
            ps2.setString(1, adminId);
            ps2.setString(2, email);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                out.println("<script>alert('Incorrect Email for this Admin ID!'); window.location='login.jsp';</script>");
                return;
            }

            //  Check password for this admin ID & email
            PreparedStatement ps3 = con.prepareStatement("SELECT * FROM admin WHERE admin_id = ? AND email = ? AND password = ?");
            ps3.setString(1, adminId);
            ps3.setString(2, email);
            ps3.setString(3, password);
            ResultSet rs3 = ps3.executeQuery();

            if (rs3.next()) {
                // ✅ Successful login
                HttpSession session = request.getSession();
                session.setAttribute("adminId", rs3.getString("admin_id"));
                session.setAttribute("adminName", rs3.getString("name"));
                session.setAttribute("adminEmail", rs3.getString("email"));

                response.sendRedirect(request.getContextPath() + "/Admin/adminDashboard.jsp");
            } else {
                out.println("<script>alert('Incorrect Password!'); window.location='login.jsp';</script>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
