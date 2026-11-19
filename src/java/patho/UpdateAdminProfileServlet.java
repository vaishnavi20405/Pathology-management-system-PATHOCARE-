package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/UpdateAdminProfileServlet")
public class UpdateAdminProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String admin_id = request.getParameter("admin_id");
        String phone = request.getParameter("phone_no");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");

            PreparedStatement ps = con.prepareStatement(
                "UPDATE admin SET phone_no=?, email=?, password=? WHERE admin_id=?");
            ps.setString(1, phone);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, admin_id);

            int i = ps.executeUpdate();
            if (i > 0) {
                out.println("<script>alert('Profile Updated Successfully!'); window.location='adminProfile.jsp';</script>");
            } else {
                out.println("<script>alert('Update Failed!'); window.location='adminProfile.jsp';</script>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
