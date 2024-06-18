package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.formation;
import Services.serviceFormation;

public class AjouterFormation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker DD;

    @FXML
    private DatePicker DF;

    @FXML
    private TextField cout;

    @FXML
    private TextField description;

    @FXML
    private TextField duree;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nomc;

    @FXML
    private TextField nomf;

    private final serviceFormation FS= new serviceFormation();

    private Date convertToUtilDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        if (nomc.getText().isEmpty() || nomf.getText().isEmpty() || description.getText().isEmpty() || duree.getText().isEmpty()
                || niveau.getText().isEmpty()  || DD.getValue() == null || DF.getValue() == null|| cout.getText().isEmpty()) {
            // Display error message to user
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Wrong date");
            successAlert.setHeaderText("Please fill in all fields");
            successAlert.showAndWait();
        }

        // Convert date pickers to Date objects
        LocalDate startDate = DD.getValue();
        LocalDate endDate = DF.getValue();

        // Check if the selected dates are from today onwards
        LocalDate today = LocalDate.now();
        if (startDate.isBefore(today) || endDate.isBefore(today)) {
            // Display error message to user
            // Display success message to user
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Wrong date");
            successAlert.setHeaderText("Please select dates from today onwards");
            successAlert.showAndWait();
            return;
        }

        // Check if the start date is after the end date
        if (startDate.isAfter(endDate)) {
            // Display error message to user
            System.out.println("Start date cannot be after end date");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Wrong date");
            successAlert.setHeaderText("Start date cannot be after end date");
            successAlert.showAndWait();
            return;
        }

        Date dateDebut = convertToUtilDate(startDate);
        Date dateFin = convertToUtilDate(endDate);

        // Validate the durationText and Prix fields


            FS.ajouter(new formation(
                    nomc.getText(),
                    nomf.getText(),
                    description.getText(),
                    duree.getText(),
                    niveau.getText(),
                    dateDebut,
                    dateFin,
                    cout.getText()
            ));

            // Display success message to user
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Formation added successfully");
            successAlert.showAndWait();


    }


    @FXML
    void aficher(ActionEvent event) {

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/AfficherFormation.fxml"));
        try {
            Parent root = loader1.load();
            AfficherFormation controller = loader1.getController();
            nomc.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void initialize() {
        assert DD != null : "fx:id=\"DD\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert DF != null : "fx:id=\"DF\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert cout != null : "fx:id=\"cout\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert duree != null : "fx:id=\"duree\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert niveau != null : "fx:id=\"niveau\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert nomc != null : "fx:id=\"nomc\" was not injected: check your FXML file 'AjouterFormation.fxml'.";
        assert nomf != null : "fx:id=\"nomf\" was not injected: check your FXML file 'AjouterFormation.fxml'.";

    }

    public void quiz(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void dispo(ActionEvent event) {
    }

    public void event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void profil(ActionEvent event) {
    }

    public void reclama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void formations(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void remise(ActionEvent actionEvent) {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/remise.fxml"));
        try {
            Parent root = loader1.load();
            RemiseManagment controller = loader1.getController();
            cout.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void salle(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SalleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void club(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClubManagement.fxml"));
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

    public void verspageadus(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
    @FXML
    void listep(ActionEvent event) {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/admin.fxml"));
        try {
            Parent root = loader1.load();
            Admin controller = loader1.getController();
            cout.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }








    public void pai(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
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








}
