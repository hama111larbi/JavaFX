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
import javafx.stage.Stage;
import Services.ServicePaiement;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.application.Application.launch;

public  class Admin {
    @FXML
    private TableView<Payment> afficher;

    @FXML
    private TableColumn<Payment, String> modeColumn;

    @FXML
    private TableColumn<Payment, Integer> montantColumn;

    @FXML
    private TextField mode;

    @FXML
    private TextField montant;
    @FXML
    private Button generatePDFButton;


    ServicePaiement rp =new ServicePaiement();

    @FXML
    void initialize() throws SQLException {
        modeColumn.setCellValueFactory(new PropertyValueFactory<>("moyendepaiement"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        afficherr();
    }
    public void afficherr() throws SQLException {
        ObservableList<Payment> list = rp.afficher();
        afficher.setItems(list);
    }
    public void refreshTableView() {
        try {
            afficherr();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void selection() throws SQLException {
        Payment p = afficher.getSelectionModel().getSelectedItem();
        if (p != null) {
            mode.setText(p.getMoyendepaiement());
            montant.setText(String.valueOf(p.getMontant()));
        }
    }


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

    @FXML
    void supp(ActionEvent event) throws IOException, SQLException {
        Payment p=afficher.getSelectionModel().getSelectedItem();
        System.out.println(p.getId());
        rp.supprimer(p.getId());
        afficherr();
        afficher.refresh();
    }









    public void quiz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/questions1.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void ajouter(ActionEvent event) {
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
}