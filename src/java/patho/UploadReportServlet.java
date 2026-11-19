package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@WebServlet("/UploadReportServlet")
public class UploadReportServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String patientId = request.getParameter("patientId");
//        String patientName = request.getParameter("patientName");
//        String email = request.getParameter("email");
        String testName = request.getParameter("testName");

//        System.out.println("Email recived: "+email);
        
        // ✅ Get PDF from project "uploads" folder
        String filePath = getServletContext().getRealPath("/uploads/receipts.pdf");
        File pdfFile = new File(filePath);

        if (!pdfFile.exists()) {
            System.out.println("❌ File not found at: " + filePath);
            response.sendRedirect("uploadReport.jsp?msg=nofile");
            return;
        }

        // ✅ Email sending details
        final String senderEmail = "pathocarelaboratory3@gmail.com";
        final String senderPassword = "hsxz kfuy ciey xwvs";

            Properties props = System.getProperties();
        props.put(Keys.HOST, "smtp.gmail.com");
        props.put(Keys.PORT, "465");
        props.put(Keys.AUTH, "true");
        props.put(Keys.SSL_ENABLED, "true");
        props.put(Keys.SOCKET_FACTORY_PORT, "465");
        props.put(Keys.SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        props.put(Keys.SOCKET_FACTORY_FALLBACK, "false");


            // Authenticate
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(request.getSession().getAttribute("MailForsending").toString()));
            message.setSubject("Your Test Report - " + testName);

            // ✅ Email Body + Attachment
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Hello " + request.getSession().getAttribute("PatientName").toString() + ",\n\nPlease find attached your report for: "
                    + testName + ".\n\nRegards,\nPathocare Laboratory");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(pdfFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("✅ Email Sent Successfully!");

                response.sendRedirect(request.getContextPath() + "/Admin/adminDashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("uploadReport.jsp");
        }
    }
}
