package Controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import entity.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.controlsfx.control.Notifications;
import Services.ServicePaiement;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PaiementManagment {

    @FXML
    private TextField Montant;

    @FXML
    private TextField Mpaiement;

    // Inject the instance of ServicePaiement
    ServicePaiement SV = new ServicePaiement();


    @FXML
    private void formation(ActionEvent event) throws SQLException, IOException {
        Stripe.apiKey = "sk_test_51P6dihBtpAjMlV8bUFIDKoPF9JdQFNc4qo6lM44lOrGMi0Iv9SdfAHu3B1J2tPB8wjshqhxiYKiIVCSRzcWTKshp00PEbveIOB";
        LocalDateTime now = LocalDateTime.now();
        SV.ajouter(new Payment(Integer.parseInt(Montant.getText()), Mpaiement.getText(), "mohamedelarbin@gmail.com", now));
        showNotification("Payment Added", "Payment has been added successfully!");

        generatePDF(Integer.parseInt(Montant.getText()), Mpaiement.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoursApprenant.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    private void showErrorDialog(String message, String eMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .show();
    }


    @FXML
    private void ajouter() {
        // Set your secret key here
        Stripe.apiKey = "sk_test_51P6dihBtpAjMlV8bUFIDKoPF9JdQFNc4qo6lM44lOrGMi0Iv9SdfAHu3B1J2tPB8wjshqhxiYKiIVCSRzcWTKshp00PEbveIOB";

        try {
            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(20000L) // Amount in cents (e.g., $200.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // Log the created PaymentIntent details
            System.out.println("PaymentIntent created: " + intent.toJson());

            // If the payment was successful, display a success message
            if ("succeeded".equals(intent.getStatus())) {
                System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
                showNotification("Payment Successful", "Payment has been processed successfully.");
                showAlert("Payment successful."); // Display an alert dialog
            } else {
                System.out.println("Payment failed. PaymentIntent status: " + intent.getStatus());
                showErrorDialog("Payment Failed", "Payment failed. PaymentIntent status: " + intent.getStatus());
            }
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
            showErrorDialog("Payment Failed", e.getMessage());
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void generatePDF(int montant, String mpaiement) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Paiement Details");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Montant: " + montant);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mode de Paiement: " + mpaiement);
                contentStream.endText();
            }

            String pdfFilePath = "Paiement_" + System.currentTimeMillis() + ".pdf";
            document.save(pdfFilePath);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(pdfFilePath));
            } else {
                System.out.println("Desktop is not supported on this platform.");
            }
        }
    }

    public void reclama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemRec.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    public void quiz(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    public void event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface etudiant.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    public void salle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }



    public void remise(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/remise.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }

    public void equipement(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void pai(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }



    public void disc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void formations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFormationApprenant.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void cours(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoursApprenant.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }




    public void club(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClubMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void dispo(ActionEvent actionEvent) {
    }


    public void profil(ActionEvent actionEvent) {
    }

    public void salles(ActionEvent actionEvent) {
    }

    public void emplacement(ActionEvent actionEvent) {
    }
}
