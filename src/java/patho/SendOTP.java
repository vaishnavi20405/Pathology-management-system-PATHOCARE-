package patho;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendOTP {

    public static boolean sendEmail(String toEmail, String otp) {
        boolean status = false;

        // Gmail credentials
        final String fromEmail = "pathocarelaboratory3@gmail.com"; // apna Gmail daalo
        final String appPassword = "hsxz kfuy ciey xwvs";  // yaha 16 digit App Password daalo (normal password NHI)

        try {
            // SMTP properties
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
                    return new PasswordAuthentication(fromEmail, appPassword);
                }
            });
            
            session.setDebug(true);

            // Message create
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);

            // Send mail
            Transport.send(message);

            System.out.println("OTP sent successfully to: " + toEmail);
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
