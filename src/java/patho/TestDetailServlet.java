package patho;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/TestDetailServlet")
public class TestDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM test WHERE test_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("name", rs.getString("test_name"));
                request.setAttribute("description", rs.getString("description"));
                request.setAttribute("price", rs.getString("price"));
            }

            RequestDispatcher rd = request.getRequestDispatcher("testDetails.jsp");
            rd.forward(request, response);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
