package patho;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;

@WebServlet("/AutoCompleteServlet")
public class AutoCompleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String query = request.getParameter("term");
        List<String> testNames = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
            PreparedStatement ps = con.prepareStatement(
                "SELECT test_name FROM test WHERE LOWER(test_name) LIKE LOWER(?) LIMIT 10");
            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                testNames.add(rs.getString("test_name"));
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(testNames);
        response.getWriter().write(json);
    }
}
