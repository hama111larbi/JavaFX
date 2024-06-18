package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.formation;
import Services.serviceFormation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Affichageuser {
    @FXML
    private VBox affichageformationvbox;

    @FXML
    private Label coutLabel;

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Label niveauLabel;

    public VBox getAffichageformationvbox() {
        return affichageformationvbox;
    }

    public void setAffichageformationvbox(VBox affichageformationvbox) {
        this.affichageformationvbox = affichageformationvbox;
    }

    public Label getCoutLabel() {
        return coutLabel;
    }

    public void setCoutLabel(Label coutLabel) {
        this.coutLabel = coutLabel;
    }

    public Label getDateDebutLabel() {
        return dateDebutLabel;
    }

    public void setDateDebutLabel(Label dateDebutLabel) {
        this.dateDebutLabel = dateDebutLabel;
    }

    public Label getDateFinLabel() {
        return dateFinLabel;
    }

    public void setDateFinLabel(Label dateFinLabel) {
        this.dateFinLabel = dateFinLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescriptionLabel(Label descriptionLabel) {
        this.descriptionLabel = descriptionLabel;
    }

    public Label getDureeLabel() {
        return dureeLabel;
    }

    public void setDureeLabel(Label dureeLabel) {
        this.dureeLabel = dureeLabel;
    }

    public Label getNiveauLabel() {
        return niveauLabel;
    }

    public void setNiveauLabel(Label niveauLabel) {
        this.niveauLabel = niveauLabel;
    }

    public Label getNomCategorieLabel() {
        return nomCategorieLabel;
    }

    public void setNomCategorieLabel(Label nomCategorieLabel) {
        this.nomCategorieLabel = nomCategorieLabel;
    }

    public Label getNomFormationLabel() {
        return nomFormationLabel;
    }

    public void setNomFormationLabel(Label nomFormationLabel) {
        this.nomFormationLabel = nomFormationLabel;
    }

    @FXML
    private Label nomCategorieLabel;

    @FXML
    private Label nomFormationLabel;
    private serviceFormation FS = new serviceFormation();

    public void AfficherDB(ActionEvent actionEvent) {
        try {
            List<formation> formations = FS.afficher();

            // Clear any existing items in the VBox
            affichageformationvbox.getChildren().clear();
            affichageformationvbox.setStyle("-fx-background-color: #ADD8E6;"); // Définir la couleur de fond bleu ciel


            // Loop through each Formation object and add its details to the VBox
            for (int i = 0; i < formations.size(); i += 4) {
                // Create a new HBox for each row
                HBox rowBox = new HBox();
                rowBox.setSpacing(20); // Adjust spacing between formations in a row

                // Loop through 4 formations and add them to the current row
                for (int j = i; j < Math.min(i + 4, formations.size()); j++) {
                    formation formation = formations.get(j);

                    // Create labels for each property of the Formation object
                    Label coutLabel = new Label("Titre: " + formation.getNomCategorie());
                    // Set the ID of the label to the ID of the formation
                    coutLabel.setId(String.valueOf(formation.getID_de_Formation()));

                    // Create other labels for the remaining properties of the Formation object
                    Label nomformationLabel = new Label("Nom de Formation: " + formation.getNom_de_Formation());
                    Label descriptionLabel = new Label("Description: " + formation.getDescription());
                    Label dureeLabel = new Label("Durée: " + formation.getDurée() + " heures");
                    Label dateDebutLabel = new Label("Date Début: " + formation.getDate_Deb());
                    dateDebutLabel.setPrefWidth(120); // Set the preferred width as needed
                    Label dateFinLabel = new Label("Date Fin: " + formation.getDate_Fin());
                    dateFinLabel.setPrefWidth(120); // Set the preferred width as needed
                    Label prixLabel = new Label("Prix: " + formation.getCoût() + " DT");
                    Label niveauLabel = new Label("Niveau: " + formation.getNiveau());

//                     Optionally, you can add an image to the formation
//                    ImageView imageView = new ImageView(new Image("/cours.png"));
//                    imageView.setFitWidth(100);
//                    imageView.setPreserveRatio(true);
// Create a button to access the detailed view of the formation
                    Button detailsButton = new Button("aceder cours");
                    detailsButton.setOnAction(e -> {
                        // Load the coursuser.fxml file when the button is clicked
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/coursuser.fxml"));
                        try {
                            Parent root = loader.load();
                            Coursuser controller = loader.getController();
                            // Pass the ID of the selected formation to the Coursuser controller
                            controller.setFormationId(formation.getID_de_Formation());
                            affichageformationvbox.getScene().setRoot(root);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    });

// Add labels and button to the current VBox
                    VBox formationBox = new VBox( nomformationLabel, descriptionLabel, dureeLabel, dateDebutLabel, dateFinLabel, prixLabel, niveauLabel, detailsButton);
                    formationBox.setSpacing(5); // Adjust spacing between labels in a formation

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

    public void formations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void cours(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }


    @FXML
    void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }



    @FXML
    void salle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }


    public void event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void club(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClubMangement.fxml"));
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

    public void disc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }



    public void pai(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void verspageadus(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    public void quiz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/questions1.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    }