//package Controller;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//public class EmailSender {
//
//    public static void sendEmail(String fromEmail, String toEmail, String subject, String message) {
//        // Set up mail properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP host
//        properties.put("mail.smtp.port", "587"); // Replace with your SMTP port
//
//        // Create a session with an authenticator
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("mohamedelarbin@gmail.com", "trpx dpgy wkiv ztoa ");
//            }
//        });
//
//        // Enable debugging
//        session.setDebug(true);
//
//        try {
//            // Create a MimeMessage object
//            MimeMessage mimeMessage = new MimeMessage(session);
//
//            // Set the sender's email address
//            mimeMessage.setFrom(new InternetAddress(fromEmail));
//
//            // Set the recipient's email address
//            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//
//            // Set the email subject
//            mimeMessage.setSubject(subject);
//
//            // Set the email message
//            mimeMessage.setText(message);
//
//            // Send the email
//            Transport.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            // Handle the exception as needed
//        }
//    }
//}
//
