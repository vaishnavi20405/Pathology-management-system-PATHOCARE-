package patho;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/FetchPatientDetailsServlet")
public class FetchPatientDetailsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientId = request.getParameter("patientId"); // Admin enters patient ID
        System.out.println("Received Patient ID: " + patientId);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            // ✅ Step 1: Fetch Patient Details
            String sql1 = "SELECT name, email FROM patient WHERE patient_id = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, patientId);
            rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No patient found!");
                response.sendRedirect("Admin/uploadReport.jsp?msg=invalidId");
                return;
            }

            String patientName = rs.getString("name");
            String patientEmail = rs.getString("email");
            System.out.println("Patient Found: " + patientName + " | " + patientEmail);
            HttpSession session = request.getSession();
            session.setAttribute("MailForsending",patientEmail);
            // ✅ Step 2: Fetch Test Names for this Patient
            String sql2 = "SELECT test_name FROM bookings WHERE patient_id = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, patientId);
            rs = ps.executeQuery();

            ArrayList<String> testList = new ArrayList<>();
            while (rs.next()) {
                testList.add(rs.getString("test_name"));
            }

            if (testList.isEmpty()) {
                System.out.println("No tests found for this patient");
                response.sendRedirect("Admin/uploadReport.jsp?msg=noTests");
                return;
            }

            // ✅ Set Data for JSP
            request.setAttribute("patientId", patientId);
            request.setAttribute("patientName", patientName);
            request.setAttribute("patientEmail", patientEmail);
            request.setAttribute("testList", testList);
            session.setAttribute("PatientName",patientName);
            RequestDispatcher rd = request.getRequestDispatcher("Admin/uploadReport.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Admin/uploadReport.jsp?msg=error");

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }
}
