package Controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.salleee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Services.ServiceSalle2;

public class SalleManagement {


    @FXML
    private TextField matiere;
    @FXML
    private TextField searchTextField;
    @FXML private Button search;

    @FXML
    private ListView<salleee> afficher;
    @FXML
    private Button afficherDB;


    private ServiceSalle2 SS = new ServiceSalle2();
    private salleee selectedSalle; // Store the selected salle



    private void selection() {
        salleee p = afficher.getSelectionModel().getSelectedItem();
        if (p != null) {

            matiere.setText(String.valueOf(p.getMatiere()));
        }
    }

    @FXML
    void equipement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {

        String matiereValue = matiere.getText();

        if (!isValidMatiere(matiereValue)) {
            showAlert("Invalid Matiere", "Please enter a valid matiere (java, web, marketing, design).");
            return;
        }

        salleee nouvelleSalle = new salleee( matiereValue);

        try {
            SS.ajouter(nouvelleSalle);

            // Generate QR Code and get BufferedImage


            // Send email with QR Code


            // Refresh the list
            afficherr();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }




    private void handleSQLException(SQLException e) {
        // Handle SQL exceptions
    }

    private boolean isValidMatiere(String matiere) {
        String[] validMatieres = {"java", "web", "marketing", "design"};
        for (String validMatiere : validMatieres) {
            if (validMatiere.equalsIgnoreCase(matiere)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void modifier(ActionEvent actionEvent) throws SQLException {
        salleee p = afficher.getSelectionModel().getSelectedItem();
        if (p != null) {

            String matiereValue = matiere.getText();
            salleee updatedSalle = new salleee(matiereValue);
            SS.modifier(updatedSalle, p.getId());
            afficherr();
        } else {
            showAlert("Selection Error", "No item selected for modification.");
        }
    }

    @FXML
    void supprimer(ActionEvent event) throws SQLException {
        salleee selectedSalle = afficher.getSelectionModel().getSelectedItem();
        if (selectedSalle != null) {
            SS.supprimer(selectedSalle, selectedSalle.getId());
            afficherr();
        } else {
            showAlert("Selection Error", "No item selected for deletion.");
        }
    }

    private void afficherr() throws SQLException {
        List<salleee> salles = SS.afficher();
        ObservableList<salleee> observableList = FXCollections.observableArrayList(salles);
        afficher.setItems(observableList);
    }

    @FXML
    void search(ActionEvent actionEvent) {
        String searchTerm = searchTextField.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            try {
                afficherr(); // Restore the initial data
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }


        ObservableList<salleee> allSalles = afficher.getItems();
        ObservableList<salleee> searchResults = FXCollections.observableArrayList();

        for (salleee salle : allSalles) {
            if (salle.getMatiere().toLowerCase().contains(searchTerm)) {
                searchResults.add(salle);
            }
        }

        afficher.setItems(searchResults);
    }

    @FXML
    void sort(ActionEvent actionEvent) {
        ObservableList<salleee> salles = afficher.getItems();
        salles.sort(Comparator.comparing(salleee::getMatiere));
        afficher.setItems(FXCollections.observableArrayList(salles));
    }

    @FXML
    void afficherDB(ActionEvent event) {
        try {
            List<salleee> salles = SS.afficher();
            afficher.setItems(FXCollections.observableArrayList(salles));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while fetching the data from the database.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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



    public void remise(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/remise.fxml"));
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

    public void formations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void cours(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void salles(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
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
}