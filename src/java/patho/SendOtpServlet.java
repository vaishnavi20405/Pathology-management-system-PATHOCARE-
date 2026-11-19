package patho;

import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SendOtpServlet")
public class SendOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String email = request.getParameter("email").trim();
        // Generate OTP
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        // Save to session
        HttpSession session = request.getSession();
        session.setAttribute(Keys.OTP, otp.trim());
        session.setAttribute(Keys.OTP_TIME, System.currentTimeMillis());
        session.setAttribute(Keys.OTP_EMAIL, email.trim());

        // Send OTP via email
        boolean sent = SendOTP.sendEmail(email, otp);

        // Set response type and return plain message
        response.setContentType("text/plain");
        if (sent) {
            response.getWriter().write("OTP sent to " + email);
        } else {
            response.getWriter().write("Error sending OTP.");
        }
    }
}
