//package Controller;
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TextField;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import test.HelloApplication;
///*
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;*/
//import java.io.File;
//import java.io.IOException;
//import java.util.Properties;
//
//public class eemail extends Application {
//    private File selectedFile;
//    @FXML
//    private void attachFile() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choose a File");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
//                new FileChooser.ExtensionFilter("All Files", "*.*"));
//        Stage stage = (Stage) txtFromEmail.getScene().getWindow();
//        selectedFile = fileChooser.showOpenDialog(stage);
//
//        if (selectedFile != null) {
//            showAlert("File Attached", "File: " + selectedFile.getName());
//
//            // Set the file path in the txtMessage TextField
//            txtMessage.setText(selectedFile.getAbsolutePath());
//        }
//    }
//
//
//    @FXML
//    private TextField txtFromEmail;
//
//    @FXML
//    private TextField txtToEmail;
//
//    @FXML
//    private TextField txtSubject;
//
//    @FXML
//    private TextField txtMessage;
//
//    @FXML
//    private void sendEmail() {
//        String fromEmail = txtFromEmail.getText();
//        String toEmail = txtToEmail.getText();
//        String subject = txtSubject.getText();
//        String message = txtMessage.getText();
//
//        if (fromEmail.isEmpty() || toEmail.isEmpty() || subject.isEmpty() || message.isEmpty()) {
//            showAlert("Error", "All fields must be filled");
//            return;
//        }
//
//        // Set up mail properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");// Replace with your SMTP host
//        properties.put("mail.smtp.port", "587"); // Replace with your SMTP port
//
//
//        // Create a session with an authenticator
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("mohamedelarbin@gmail.com", "trpx dpgy wkiv ztoa ");
//            }
//        });
//
//        try {
//            // Create a MimeMessage object
//            Message mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress(fromEmail));
//            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//            mimeMessage.setSubject(subject);
//
//            // Create a MimeMultipart to hold both text and file parts
//            MimeMultipart multipart = new MimeMultipart();
//
//            // Add text part
//            MimeBodyPart textBodyPart = new MimeBodyPart();
//            textBodyPart.setText(message);
//            multipart.addBodyPart(textBodyPart);
//
//            // Add file part if a file is selected
//            if (selectedFile != null) {
//                MimeBodyPart fileBodyPart = new MimeBodyPart();
//                fileBodyPart.attachFile(selectedFile);
//                multipart.addBodyPart(fileBodyPart);
//            }
//
//            // Set content of the message
//            mimeMessage.setContent(multipart);
//
//            // Send the email
//            Transport.send(mimeMessage);
//
//            showAlert("Success", "Email sent successfully!");
//        } catch (MessagingException | IOException e) {
//            e.printStackTrace();
//            showAlert("Error", "Error sending email. Check the console for details.");
//        }
//    }
//
//
//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("email.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("acheter une formation");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//
//}
