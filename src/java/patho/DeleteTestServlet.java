package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/DeleteTestServlet")
public class DeleteTestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int test_id = Integer.parseInt(request.getParameter("test_id"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
            PreparedStatement ps = con.prepareStatement("DELETE FROM test WHERE test_id=?");
            ps.setInt(1, test_id);
            ps.executeUpdate();

            con.close();
            response.sendRedirect("Admin/manageTest.jsp");

        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
