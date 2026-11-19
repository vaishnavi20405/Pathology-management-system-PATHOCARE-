package patho;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;

@WebServlet("/AutoSuggestServlet")
public class AutoSuggestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (query == null || query.trim().isEmpty()) {
            out.print("[]");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
            PreparedStatement ps = con.prepareStatement(
                "SELECT test_name FROM test WHERE test_name LIKE ? LIMIT 5");
            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            List<String> names = new ArrayList<>();
            while (rs.next()) {
                names.add(rs.getString("test_name"));
            }

            out.print(names.toString()); // Sends list as JSON
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}
