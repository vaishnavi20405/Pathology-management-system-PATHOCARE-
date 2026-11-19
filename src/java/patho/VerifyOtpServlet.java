package patho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/VerifyOtpServlet")
public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String enteredOtp = request.getParameter("otp");
        String email = request.getParameter("email");

        HttpSession session = request.getSession(false);

        // ✅ Step 1: Session & OTP Validation
        if (session == null ||
            session.getAttribute(Keys.OTP) == null ||
            session.getAttribute(Keys.OTP_EMAIL) == null ||
            session.getAttribute(Keys.OTP_TIME) == null) {
            out.println("<script>alert('Session expired! Please try again.'); window.location='login.jsp';</script>");
            return;
        }

        String sessionOtp = session.getAttribute(Keys.OTP).toString();
        String sessionEmail = session.getAttribute(Keys.OTP_EMAIL).toString();
        Long otpTime = (Long) session.getAttribute(Keys.OTP_TIME);

        // ✅ Step 2: OTP expiry check (5 minutes)
        long now = System.currentTimeMillis();
        if (otpTime == null || (now - otpTime > 5 * 60 * 1000)) {
            clearOtpSession(session);
            out.println("<script>alert('OTP expired! Please request again.'); window.location='login.jsp';</script>");
            return;
        }

        // ✅ Step 3: Validate OTP
        if (enteredOtp.equals(sessionOtp) && email.equals(sessionEmail)) {

            try (Connection con = DBConnection.getConnection()) {

                // ✅ Fetch full patient details including ID
                PreparedStatement ps = con.prepareStatement(
                        "SELECT patient_id, name, email FROM patient WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int patientId = rs.getInt("patient_id");
                    String patientName = rs.getString("name");
                    String patientEmail = rs.getString("email");
                    System.out.println("✅ VerifyOtpServlet: patientId = " + patientId);
                    System.out.println("✅ VerifyOtpServlet: patientName = " + patientName);

                    // ✅ Save patient info in session
                    session.setAttribute("patientId", patientId);
                    session.setAttribute("patientName", patientName);
                    session.setAttribute("patientEmail", patientEmail);
                    System.out.println("[DEBUG] ✅ Patient ID stored in session after OTP verify: " + session.getAttribute("patientId"));

                    // 🧹 Clear OTP details
                    clearOtpSession(session);

                    // ✅ Redirect to patient dashboard
                    response.sendRedirect(request.getContextPath() + "/Patient/patientDashboard.jsp");
                } else {
                    out.println("<script>alert('No user found with this email!'); window.location='login.jsp';</script>");
                }

            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            }

        } else {
            out.println("<script>alert('Invalid OTP! Please try again.'); window.location='login.jsp';</script>");
        }
    }

    // 🧹 Helper to clear OTP data from session
    private void clearOtpSession(HttpSession session) {
        session.removeAttribute(Keys.OTP);
        session.removeAttribute(Keys.OTP_EMAIL);
        session.removeAttribute(Keys.OTP_TIME);
    }
}
