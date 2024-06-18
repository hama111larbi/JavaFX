package Controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import Services.serviceFormation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AfficherFormationApprenant implements Initializable {
    @FXML
    private Label FromDB;
    @FXML
    private Label DD;

    @FXML
    private Label DF;

    @FXML
    private Label Description;

    @FXML
    private Label Duree;

    @FXML
    private Label Niveau;

    @FXML
    private Label NomC;

    @FXML
    private Label NomF;

    @FXML
    private Label Prix;


    @FXML
    private WebView affichageWeb;

    public String getDescription() {
        return Description.getText();
    }

    public void setDescription(String description) {
        Description.setText(description);
    }

    public String getDuree() {
        return Duree.getText();
    }

    public void setDuree(String duree) {
        Duree.setText(duree);
    }

    public String getNiveau() {
        return Niveau.getText();
    }

    public void setNiveau(String niveau) {
        Niveau.setText(niveau);
    }

    public String getNomF() {
        return NomF.getText();
    }

    public void setNomF(String nomF) {
        NomF.setText(nomF);
    }

    public String getPrix() {
        return Prix.getText();
    }

    public void setPrix(String prix) {
        Prix.setText(prix);
    }

    public String getNomC() {
        return NomC.getText();
    }

    public void setNomC(String nomC) {
        NomC.setText(nomC);
    }

    public LocalDate getDD() {
        return LocalDate.parse(DD.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDD(LocalDate dd) {
        DD.setText(dd.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public LocalDate getDF() {
        return LocalDate.parse(DF.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDF(LocalDate df) {
        DF.setText(df.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


    private serviceFormation FS = new serviceFormation();


    @FXML
    private ListView<String> formationListView;
    private List<formation> formations;
    @FXML
    private VBox affichageformationvbox;
    @FXML
    private TextField titreFormationTextField; // Assuming you have a TextField to input the ID
    private serviceFormation formationService; // Instance of your service
    private String selectedItem;
    private formation selectedFormation;
    @FXML
    private AnchorPane anchorPane;

    private boolean darkMode = false;
    private List<formation> allData;
    private int currentPageIndex = 0;
    private int itemsPerPage = 4;

    @FXML
    private void toggleBackgroundColor(ActionEvent event) {
        if (darkMode) {
            anchorPane.setStyle("-fx-background-color: #ffffff;");
        } else {
            anchorPane.setStyle("-fx-background-color: #2b2b2b;");
        }
        darkMode = !darkMode;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Leave this method empty if you're using JavaFX to inject the Label
        // FromDB will be automatically injected by JavaFX
        AfficherFormation(null);
        loadPage(0);

    }


    @FXML
    private void handleLabelClick(MouseEvent event) {
        Node source = (Node) event.getSource();

        if (source instanceof Label) {
            Label clickedLabel = (Label) source;

            // Retrieve the Formation object associated with the clicked label
            formation clickedFormation = (formation) clickedLabel.getUserData();

            if (clickedFormation != null) {
                // Set the selectedFormation to the clickedFormation
                selectedFormation = clickedFormation;

                // Display both the title and the ID of the selected formation
                String message = "categorie label clicked for formation: " + clickedFormation.getNomCategorie() + "\n";
                message += "Formation ID: " + formation.getID_de_Formation();
                FromDB.setText(message);

                // After modifying a formation, update the FromDB label
                String updatedInfo = "Updated formation with ID: " + formation.getID_de_Formation() + ", New title: " + clickedFormation.getNomCategorie();
                FromDB.setText(updatedInfo);

                // Reload the page to reflect the updated data
                loadPage(currentPageIndex);
            } else {
                System.out.println("No formation associated with the clicked label.");
            }
        }
    }
    @FXML
    void AfficherFormation(ActionEvent event) {
        try {
            List<formation> formations = FS.afficher();


            // Clear any existing items in the VBox
            affichageformationvbox.getChildren().clear();

            // Loop through each Formation object and add its details to the VBox
            for (int i = 0; i < formations.size(); i += 4) {
                // Create a new HBox for each row
                HBox rowBox = new HBox();
                rowBox.setSpacing(20); // Adjust spacing between formations in a row

                // Loop through 4 formations and add them to the current row
                for (int j = i; j < Math.min(i + 4, formations.size()); j++) {
                    formation formation = formations.get(j);

                    // Create labels for each property of the Formation object
                    Label titreLabel = new Label("Categorie: " + formation.getNomCategorie());
                    // Set the ID of the label to the ID of the formation
                    titreLabel.setId(String.valueOf(models.formation.getID_de_Formation()));

                    // Set the Formation object as the user data for the label
                    titreLabel.setUserData(formation);

                    // Add event handler to titreLabel
                    titreLabel.setOnMouseClicked(this::handleLabelClick);

                    // Create other labels for the remaining properties of the Formation object
                    // (Description, Durée, Date Début, Date Fin, Prix, Niveau)
                    Label descriptionLabel = new Label("Description: " + formation.getDescription());
                    Label dureeLabel = new Label("Durée: " + formation.getDurée() + " heures");
                    Label dateDebutLabel = new Label("Date Début: " + formation.getDate_Deb());
                    dateDebutLabel.setPrefWidth(120); // Set the preferred width as needed
                    Label dateFinLabel = new Label("Date Fin: " + formation.getDate_Fin());
                    dateFinLabel.setPrefWidth(120); // Set the preferred width as needed
                    Label prixLabel = new Label("Prix: " + formation.getCoût() + " DT");
                    Label niveauLabel = new Label("Niveau: " + formation.getNiveau());



                    // Optionally, you can add an image to the formation
                    ImageView imageView = new ImageView(new Image("/src/cours.png"));
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);


                    // Add labels and image to the current VBox
                    VBox formationBox = new VBox(imageView, titreLabel, descriptionLabel, dureeLabel, dateDebutLabel, dateFinLabel, prixLabel, niveauLabel);
                    formationBox.setSpacing(5); // Adjust spacing between labels in a formation

                    allData = FS.afficher();
                    // Load the first page
                    loadPage(currentPageIndex);


                    // Add the VBox for the current formation to the row
                    rowBox.getChildren().add(formationBox);
                }

                // Add the current row to the VBox
                affichageformationvbox.getChildren().add(rowBox);

                // Add spacing between rows
                Region spacer = new Region();
                spacer.setPrefHeight(40); // Adjust the height to increase or decrease the spacing between rows
                affichageformationvbox.getChildren().add(spacer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper method to parse the ID from the string representation of a Formation object
    private int parseIdFromSelectedItem(String selectedItem) {
        // Assuming your string representation is in the format "Formation{idFormation=<id>, ...}"
        int startIndex = selectedItem.indexOf("idFormation=") + "idFormation=".length();
        int endIndex = selectedItem.indexOf(",", startIndex);
        return Integer.parseInt(selectedItem.substring(startIndex, endIndex));
    }

    //test syrine


    // Helper method to convert Date to LocalDate
    private LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

//    public AfficherFormation() {
//        // Initialize formationService here
//        this.formationService = new ServiceFormation();
//    }

    // Helper method t to show an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    // Helper method to show an alert dialog
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void loadPreviousPage(ActionEvent actionEvent) {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            loadPage(currentPageIndex);
        }
    }

    @FXML
    void loadNextPage(ActionEvent actionEvent) {
        int totalPages = getTotalPages();
        if (currentPageIndex < totalPages - 1) {
            currentPageIndex++;
            loadPage(currentPageIndex);
        }
    }

    private void loadPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, allData.size());
        affichageformationvbox.getChildren().clear();
        HBox rowBox = new HBox(); // Create a new HBox for each row
        rowBox.setSpacing(20); // Adjust spacing between formations in a row
        for (int i = fromIndex; i < toIndex; i++) {
            formation formation = allData.get(i);
            // Create labels for each property of the Formation object
            Label titreLabel = new Label(); // Create an empty label
            titreLabel.setId(String.valueOf(models.formation.getID_de_Formation()));
            titreLabel.setUserData(formation);
            titreLabel.setOnMouseClicked(this::handleLabelClick);
            // Set the text of the titreLabel dynamically with the formation's title
            titreLabel.setText("Nom Categorie: " + formation.getNomCategorie());
            Label descriptionLabel = new Label("Description: " + formation.getDescription());
            Label dureeLabel = new Label("Durée: " + formation.getDurée() + " heures");
            Label dateDebutLabel = new Label("Date Début: " + formation.getDate_Deb());
            Label dateFinLabel = new Label("Date Fin: " + formation.getDate_Fin());
            Label prixLabel = new Label("Prix: " + formation.getCoût() + " DT");
            Label niveauLabel = new Label("Niveau: " + formation.getNiveau());
            ImageView imageView = new ImageView(new Image("/src/cours.png"));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
// Generate the QR code image
            ImageView qrCodeImageView = generateQRCode(formation.getNomCategorie());

// Create a VBox to hold course details

            VBox formationBox = new VBox(imageView, titreLabel, descriptionLabel, dureeLabel, dateDebutLabel, dateFinLabel, prixLabel, niveauLabel,qrCodeImageView);
            formationBox.setSpacing(5);
            rowBox.getChildren().add(formationBox);
        }
        affichageformationvbox.getChildren().add(rowBox);
        Region spacer = new Region();
        spacer.setPrefWidth(20);
        affichageformationvbox.getChildren().add(spacer);
    }
    private int getTotalPages() {
        return (int) Math.ceil((double) allData.size() / itemsPerPage);
    }


    @FXML
    void acceder_cours(ActionEvent event) {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        try {
            Parent root = loader1.load();
            PaiementManagment controller = loader1.getController();
            affichageformationvbox.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private ImageView generateQRCode(String text) {
        ImageView qrCodeImageView = null;
        try {
            // Set up the QR code generation parameters
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 100, 100, hints);

            // Convert the BitMatrix to an image
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);

            // Create an ImageView with the QR code image
            qrCodeImageView = new ImageView(new Image(new ByteArrayInputStream(out.toByteArray())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCodeImageView;
    }


}