package Controller;

import entity.Remise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.ServciceRemise;

import java.io.IOException;
import java.sql.SQLException;

public class RemiseManagment {
    @FXML
    private TextField montant;
    @FXML
    private Button gotoemail;

    @FXML
    private Button Ajouter;

    @FXML
    private TextField Maprèsp;

    @FXML
    private TextField Premise;


    @FXML
    private ListView<Remise> afficher;
    @FXML
    private Button uploadButton;






    ServciceRemise rs=new ServciceRemise();
    private void selection(){
        Remise p=afficher.getItems().get(afficher.getSelectionModel().getSelectedIndex());
        Maprèsp.setText(String.valueOf(p.getMontantaprespourcentage()));
        Premise.setText(String.valueOf(p.getPourcentage()));
    }
    @FXML
    void initialize() throws SQLException {
        afficher.setOnMouseClicked(
                event -> {
                    selection();
                }
        );

        Premise.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !montant.getText().isEmpty()) {
                try {
                    // Calculate the new amount based on the percentage
                    double percentage = Double.parseDouble(newValue);
                    double montantValue = Double.parseDouble(montant.getText());
                    double newAmount = (percentage / 100) * montantValue;

                    // Update the Maprèsp TextField with the new amount
                    Maprèsp.setText(String.valueOf(newAmount));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Handle the case where the text is not a valid number
                }
            }
        });

        afficherr();
    }



    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        float montantValue = Float.parseFloat(montant.getText());
        int pourcentageValue = Integer.parseInt(Premise.getText());
        float montantApresPourcentageValue = Float.parseFloat(Maprèsp.getText());
        Remise remise = new Remise(montantValue, pourcentageValue, montantApresPourcentageValue);
        rs.ajouter(remise);
        afficherr();
    }
    private void afficherr() throws SQLException {
        afficher.setItems(rs.afficher());
    }

    @FXML
    void modifier(ActionEvent event) throws SQLException {
        Remise selectedRemise = afficher.getSelectionModel().getSelectedItem();
        if (selectedRemise != null) {
            float montantValue = Float.parseFloat(montant.getText());
            int pourcentageValue = Integer.parseInt(Premise.getText());
            float montantApresPourcentageValue = Float.parseFloat(Maprèsp.getText());
            selectedRemise.setMontant(montantValue);
            selectedRemise.setPourcentage(pourcentageValue);
            selectedRemise.setMontantaprespourcentage(montantApresPourcentageValue);
            rs.modifier(selectedRemise, selectedRemise.getId());
            afficherr();
        } else {
            // Handle case where no item is selected
            System.out.println("No item selected for modification.");
        }
    }



    /*public void supp(ActionEvent actionEvent) throws SQLException {
        Remise p=afficher.getSelectionModel().getSelectedItem();
        System.out.println(p.getIdpaiement());
        rs.supprimer(p.getIdpaiement());
        afficherr();
        afficher.refresh();
    }*/
    public void supp(ActionEvent event) throws SQLException {
        Remise selectedRemise = afficher.getSelectionModel().getSelectedItem();
        if (selectedRemise != null) {
            rs.supprimer(selectedRemise, selectedRemise.getId());
            afficherr();
        } else {
            // Handle case where no item is selected
            System.out.println("No item selected for deletion.");
        }
    }
    @FXML
    private void handleGoToEmail(ActionEvent event) {
        try {
            // Load the FXML file for the email page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/email.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Email Page");

            // Set the scene with the email page content
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Show the stage
            stage.show();

            // Close the current stage (optional)
            Stage currentStage = (Stage) gotoemail.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
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
