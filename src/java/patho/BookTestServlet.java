package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/BookTestServlet")
public class BookTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // ✅ Retrieve session for patient ID
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("patientId") == null) {
            out.println("<script>alert('Session expired! Please log in again.');window.location='login.jsp';</script>");
            return;
        }

        int patient_id = (Integer) session.getAttribute("patientId");

        // ✅ Get form values
        String patient_name = request.getParameter("patient_name");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String blood_group = request.getParameter("blood_group");
        String test_name = request.getParameter("test_name");
        String visit_type = request.getParameter("visit_type");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String payment_method = request.getParameter("payment_method");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            // ✅ Insert booking data
            String sql = "INSERT INTO bookings (patient_id, patient_name, age, gender, blood_group, test_name, visit_type, date, time, payment_method, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, patient_id);
            ps.setString(2, patient_name);
            ps.setString(3, age);
            ps.setString(4, gender);
            ps.setString(5, blood_group);
            ps.setString(6, test_name);
            ps.setString(7, visit_type);
            ps.setString(8, date);
            ps.setString(9, time);
            ps.setString(10, payment_method);
            ps.setString(11, "Pending");

            int rows = ps.executeUpdate();

            if (rows > 0) {
                // ✅ Redirect logic based on payment type
                if ("Online".equalsIgnoreCase(payment_method)) {
                    // Redirect to static scanner page for online payment
                    response.sendRedirect(request.getContextPath() + "/Patient/paymentScanner.jsp");
                } else {
                    // If payment is Cash → directly success message
                    out.println("<script>alert('✅ Test booked successfully!');window.location='" + request.getContextPath() + "/Patient/patientDashboard.jsp';</script>");
                }
            } else {
                out.println("<script>alert('❌ Failed to book test! Please try again.');window.location='" + request.getContextPath() + "/Patient/bookAppointment.jsp';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;text-align:center;'>⚠ Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}
