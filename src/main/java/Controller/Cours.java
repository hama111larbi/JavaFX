package Controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.categorie;
import models.cours;
import models.formation;
import Services.serviceFormation;
import Services.servicecours;

public class Cours {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NomCours;

    @FXML
    private ComboBox<formation> idFormation;
    @FXML
    private WebView courseWebView;








    private servicecours sc=new servicecours();
    private servicecours serviceC = new servicecours();
    private Services.serviceFormation serviceFormation = new serviceFormation();
    private Cours selectedCours;
    @FXML
    private TableView<cours> tableviewcours;

    @FXML
    private void initialize() {
        // Set up cell factory to display only the ID of the Formation
        idFormation.setCellFactory(param -> new ListCell<formation>() {
            @Override
            protected void updateItem(formation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getNom_de_Formation()));
                }
            }
        });

        // Populate the ComboBox with existing formations
        try {
            List<formation> formations = serviceFormation.afficher();
            idFormation.getItems().addAll(formations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //courseWebView = new WebView(); // Initialize the WebView object

    }

    @FXML
    void AjouterCours(ActionEvent event) {
        // Open a file chooser dialog to select the PDF file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile == null) {
            // No file selected, return
            return;
        }


        // Retrieve the selected formation from the ComboBox
        formation selectedFormation = idFormation.getValue();
        if (selectedFormation == null) {
            // Display error message to user
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("cours vide");
            successAlert.setHeaderText("Veuillez remplir le formulaire pour ajouter un cours !!");
            successAlert.showAndWait();
            return;
        }

        // Retrieve other necessary data for the course
        String nomCours = NomCours.getText();
        byte[] descriptionBytes;
        try {
            descriptionBytes = Files.readAllBytes(selectedFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit the method if an error occurs
        }
        // Check if a course with the same name already exists for the selected formation
        try {
            boolean courseExists = serviceC.checkCourseExists(nomCours, selectedFormation.getID_de_Formation());
            if (courseExists) {
                // Display error message to user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Un cours avec le même nom existe déjà pour la formation sélectionnée !");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message
            return;
        }

        // Create a new course instance
        cours cours = new cours(8,nomCours, descriptionBytes, selectedFormation.getID_de_Formation());

        // Call the method to add the course to the database
        try {
            serviceC.ajouter(cours);

            // Display an alert for successful addition
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Course added successfully!");
            alert.showAndWait();

            // Load the selected PDF file into the WebView
            URL url = selectedFile.toURI().toURL();
            //courseWebView.getEngine().load(url.toString());
        } catch (SQLException | MalformedURLException e) {
            // Display an alert for error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error adding course: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
    @FXML
    void AfficherCours(ActionEvent event) throws SQLException {
        // Récupérer les données de la base de données
        List<cours> coursList = sc.afficher();

        // Créer les cellules pour chaque colonne
        TableColumn<cours, String> nomCoursCol = new TableColumn<>("Nom_Cours");
        nomCoursCol.setCellValueFactory(new PropertyValueFactory<>("Nom_Cours"));

        TableColumn<cours, byte[]> descriptionCoursCol = new TableColumn<>("Description_Cours");
        descriptionCoursCol.setCellValueFactory(new PropertyValueFactory<>("Description_Cours"));

        // Ajouter les colonnes à la TableView
        tableviewcours.getColumns().addAll(nomCoursCol, descriptionCoursCol);

        // Créer une ObservableList à partir des données
        ObservableList<cours> observableCoursList = FXCollections.observableArrayList(coursList);

        // Ajouter les données à la TableView
        tableviewcours.setItems(observableCoursList);
    }

    @FXML
    void accederFormation(ActionEvent event) {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/AfficherFormation.fxml"));
        try {
            Parent root = loader1.load();
            AfficherFormation controller = loader1.getController();
            tableviewcours.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void AccederCategorie(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/categorie.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    @FXML
    void ModiferButton(ActionEvent event) {
        // Récupérer la formation sélectionnée dans la TableView
        cours coursSelectionnee = tableviewcours.getSelectionModel().getSelectedItem();

        if (coursSelectionnee != null) {
            // Afficher une boîte de dialogue de modification pour le champ nomFormation
            TextInputDialog dialog = new TextInputDialog(coursSelectionnee.getNom_Cours());
            dialog.setTitle("Modifier Nom de cours");
            dialog.setHeaderText(null);
            dialog.setContentText("Nouveau nom de cours :");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newNomFormation -> {
                coursSelectionnee.setNom_Cours(newNomFormation);
                // Répercuter les changements dans la TableView
                tableviewcours.refresh();
                // Enregistrer les modifications dans la base de données
                try {
                    serviceC.modifier(coursSelectionnee);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    private String getPDFContent(byte[] pdfData) {
        try {
            // Check if the provided PDF data is null or empty
            if (pdfData == null || pdfData.length == 0) {
                // Handle the case of empty or null PDF data
                return "<p>No PDF content available</p>";
            }

            // Convert byte array to Base64 encoding
            String base64Encoded = Base64.getEncoder().encodeToString(pdfData);

            // Construct HTML content to embed the PDF
            String htmlContent = "<embed width='100%' height='100%' name='plugin' type='application/pdf' src='data:application/pdf;base64," + base64Encoded + "' />";

            return htmlContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "<p>Error loading PDF content</p>";
        }
    }


    /*private void loadPDFContent(byte[] pdfData, WebView webView) {
        try {
            // Check if the provided PDF data is null or empty
            if (pdfData == null || pdfData.length == 0) {
                // Handle the case of empty or null PDF data
                showAlert(Alert.AlertType.WARNING, "Warning", "Empty PDF", "The PDF content is empty.");
                return;
            }

            // Load the PDF content into the WebView
            webView.getEngine().loadContent(getPDFContent(pdfData), "application/pdf");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "PDF Loading Error", "An error occurred while loading PDF content.");
        }
    }
    @FXML
    void supprimerCours(ActionEvent event) throws SQLException {
        // Récupérer la formation sélectionnée dans la TableView
        cours coursSelectionnee = tableviewcours.getSelectionModel().getSelectedItem();

        // Supprimer la formation de la base de données et de la TableView
        if (coursSelectionnee != null) {
            serviceC.supprimer(coursSelectionnee);
            tableviewcours.getItems().remove(coursSelectionnee);
        }
    }*/




    public void formations(ActionEvent actionEvent) {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        try {
            Parent root = loader1.load();
            AjouterFormation controller = loader1.getController();
            NomCours.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public void listp(ActionEvent actionEvent) {

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/admin.fxml"));
        try {
            Parent root = loader1.load();
            Admin controller = loader1.getController();
            NomCours.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void remise(ActionEvent actionEvent) {



        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/remise.fxml"));
        try {
            Parent root = loader1.load();
            RemiseManagment controller = loader1.getController();
            NomCours.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void reclama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    public void quiz(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    public void event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eventmangement.fxml"));
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





    public void equipement(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void disc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
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
}