
package patho;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ViewAppointmentsServlet")

public class ViewAppointmentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pathocare", "root", "");  // ✅ update if password

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bookings ORDER BY booking_id DESC");

            request.setAttribute("resultset", rs);
            RequestDispatcher rd = request.getRequestDispatcher("viewAppointments.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
        }
    }
}
