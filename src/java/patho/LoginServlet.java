package patho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM patient WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ Login Success - Generate OTP
                HttpSession session = request.getSession();
                session.setAttribute("patientId", rs.getInt("patient_id"));
                session.setAttribute("patientName", rs.getString("name"));
                session.setAttribute("email", rs.getString("email"));
                response.sendRedirect("Patient/dashboard.jsp");

                // Generate 6-digit OTP
                int otp = 100000 + new java.util.Random().nextInt(900000);
                session.setAttribute("otp", otp);

                // Show OTP in alert (testing purpose)
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Your OTP is: " + otp + "');");
                out.println("window.location='enterOtp.jsp';");
                out.println("</script>");

            } else {
                // ❌ Invalid Login
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid Email or Password!');");
                out.println("location='login.jsp';");
                out.println("</script>");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
