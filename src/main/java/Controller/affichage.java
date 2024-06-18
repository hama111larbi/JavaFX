package Controller;

import entity.Payment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Services.ServicePaiement;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

////import java.io.File;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class affichage {
    @FXML
    private TableView<Payment> afficher;

    @FXML
    private TableColumn<Payment, String> modeColumn;

    @FXML
    private TableColumn<Payment, Integer> montantColumn;
    //@FXML
    //private ListView<Paiement> afficher;
    @FXML
    private TextField mode;

    @FXML
    private TextField montant;
    @FXML
    private Button generatePDFButton;

    @FXML
    void modif(ActionEvent event) throws SQLException {
        Payment selectedPaiement = afficher.getSelectionModel().getSelectedItem();

        if (selectedPaiement != null) {
            // Assuming mode and montant are TextFields in your UI
            String updatedMode = mode.getText();
            int updatedMontant = Integer.parseInt(montant.getText());

            // Update the selected Paiement with the new values
            selectedPaiement.setMoyendepaiement(updatedMode);
            selectedPaiement.setMontant(updatedMontant);

            // Perform the modification in the database
            try {
                rp.modifier(selectedPaiement, selectedPaiement.getId());
                afficherr();
            } catch (SQLException e) {
                e.printStackTrace();  // Handle the exception appropriately
            }
        }
    }

    ServicePaiement rp = new ServicePaiement();

    @FXML
    void initialize() throws SQLException {
        modeColumn.setCellValueFactory(new PropertyValueFactory<>("moyendepaiement"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        generatePDFButton.setOnAction(event -> generatePDF());
        //  generatePDFButton.setOnAction(event -> generatePDF());
        afficherr();
    }

    public void afficherr() throws SQLException {
        ObservableList<Payment> list = rp.afficher();
        afficher.setItems(rp.afficher());
    }

    public void refreshTableView() {
        try {
            afficherr();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supp(ActionEvent event) throws IOException, SQLException {
        Payment p=afficher.getSelectionModel().getSelectedItem();
        System.out.println(p.getId());
        rp.supprimer(p.getId());
        afficherr();
        afficher.refresh();
    }
    public void  selection() throws SQLException {
        Payment p=afficher.getItems().get(afficher.getSelectionModel().getSelectedIndex());
        mode.setText(p.getMoyendepaiement());
        montant.setText(String.valueOf(p.getMontant()));

    }
    private void generatePDF() {
        Payment selectedPaiement = afficher.getSelectionModel().getSelectedItem();

        if (selectedPaiement != null) {
            // Use the existing PDF generation logic
            String fileName = "Payment_" + selectedPaiement.getId() + ".pdf";

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 700);
                    contentStream.showText("Paiement Details");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Mode: " + selectedPaiement.getMoyendepaiement());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Montant: " + selectedPaiement.getMontant());
                    contentStream.endText();
                }

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                File file = fileChooser.showSaveDialog(new Stage());

                if (file != null) {
                    document.save(file);
                    System.out.println("PDF generated and saved to: " + file.getAbsolutePath());

                    // Open the generated PDF file with the default PDF viewer
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(file);
                        } else {
                            System.out.println("Desktop is not supported on this platform.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Unable to open the PDF file with the default viewer.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void quiz(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void pai(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affiche.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void event(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void formations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
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

    public void remise(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/remise.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    public void club(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClubMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void equipement(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void verspageadus(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichageuser.fxml"));
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


//    private void generatePDF() {
     /*   Paiement selectedPaiement = afficher.getSelectionModel().getSelectedItem();

        if (selectedPaiement != null) {
            // Use the existing PDF generation logic
            String fileName = "Paiement_" + selectedPaiement.getIdpaiement() + ".pdf";

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 700);
                    contentStream.showText("Paiement Details");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Mode: " + selectedPaiement.getModepaiement());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Montant: " + selectedPaiement.getMontant());
                    contentStream.endText();
                }

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                File file = fileChooser.showSaveDialog(new Stage());

                if (file != null) {
                    document.save(file);
                    System.out.println("PDF generated and saved to: " + file.getAbsolutePath());

                    MysqlxCursor.Open the generated PDF file with the
                    default
                        PDF viewer
                        try {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().open(file);
                            } else {
                                System.out.println("Desktop is not supported on this platform.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Unable to open the PDF file with the default viewer.");
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/



}