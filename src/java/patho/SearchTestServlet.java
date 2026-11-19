package patho;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SearchTestServlet")
public class SearchTestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getParameter("query");
    if(query != null) query = query.trim();
        List<Map<String, String>> tests = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM test WHERE test_name LIKE ?");
            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> test = new HashMap<>();
                test.put("id", rs.getString("test_id"));
                test.put("name", rs.getString("test_name"));
                test.put("description", rs.getString("description"));
                test.put("price", rs.getString("price"));
                tests.add(test);
            }

            request.setAttribute("tests", tests);
            RequestDispatcher rd = request.getRequestDispatcher("searchResults.jsp");
            
            

            rd.forward(request, response);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
