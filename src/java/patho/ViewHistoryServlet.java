package patho;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.Booking;

@WebServlet("/ViewHistoryServlet")
public class ViewHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // ✅ Check session
        if (session == null || session.getAttribute("patientId") == null) {
            response.sendRedirect(request.getContextPath() + "/Patient/login.jsp");
            return;
        }

        int patientId = (int) session.getAttribute("patientId");
        System.out.println("[DEBUG] Logged-in patient ID (History): " + patientId);

        List<Booking> bookingList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bookings WHERE patient_id = ? ORDER BY date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setPatientId(rs.getInt("patient_id"));
                b.setPatientName(rs.getString("patient_name"));
                b.setAge(rs.getString("age"));
                b.setGender(rs.getString("gender"));
                b.setBloodGroup(rs.getString("blood_group"));
                b.setTestName(rs.getString("test_name"));
                b.setVisitType(rs.getString("visit_type"));
                b.setDate(rs.getString("date"));
                b.setTime(rs.getString("time"));
                b.setPaymentMethod(rs.getString("payment_method"));
                b.setStatus(rs.getString("status"));
                bookingList.add(b);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Send data to JSP
        request.setAttribute("bookings", bookingList);
        RequestDispatcher rd = request.getRequestDispatcher("/Patient/viewHistory.jsp");
        rd.forward(request, response);
    }
}
