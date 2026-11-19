package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/SearchPatientServlet")
public class SearchPatientServlet extends HttpServlet {

    // For dropdown autocomplete (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String query = request.getParameter("query");
        if (query == null || query.trim().equals("")) return;

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT name FROM patient WHERE LOWER(name) LIKE ? LIMIT 10");
            ps.setString(1, query.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                out.println("<option value='" + rs.getString("name") + "'></option>");
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    // For showing full patient details (GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a patient name.");
            RequestDispatcher rd = request.getRequestDispatcher("Admin/viewPatientCard.jsp");
            rd.forward(request, response);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM patient WHERE LOWER(name) LIKE ?");
            ps.setString(1, query.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("patient_id", rs.getInt("patient_id"));
                request.setAttribute("patientName", rs.getString("name"));
                request.setAttribute("patientAge", rs.getInt("age"));
                request.setAttribute("patientGender", rs.getString("gender"));
                request.setAttribute("patientContact", rs.getString("contact"));
                request.setAttribute("patientEmail", rs.getString("email"));
            } else {
                request.setAttribute("notfound", true);
            }

            RequestDispatcher rd = request.getRequestDispatcher("Admin/viewPatientCard.jsp");
            rd.forward(request, response);

            rs.close();
            ps.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
