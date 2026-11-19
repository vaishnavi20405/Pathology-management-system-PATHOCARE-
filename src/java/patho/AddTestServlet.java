package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AddTestServlet")
public class AddTestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String test_name = request.getParameter("test_name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
            PreparedStatement ps = con.prepareStatement("INSERT INTO test(test_name, description, price) VALUES (?, ?, ?)");
            ps.setString(1, test_name);
            ps.setString(2, description);
            ps.setString(3, price);

            ps.executeUpdate();
            con.close();

            response.sendRedirect("Admin/manageTest.jsp");

        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
