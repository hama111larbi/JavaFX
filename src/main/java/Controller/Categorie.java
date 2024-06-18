package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.categorie;
import models.formation;
import Services.servicecategorie;

public class Categorie {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NomC;

    @FXML
    private TableColumn<categorie, Integer> id;

    @FXML
    private TableColumn<categorie,String> nomc;

    @FXML
    private TableView<categorie> tableviewcategorie;

private servicecategorie sc=new servicecategorie();
    @FXML
    void AfficherCat(ActionEvent event) throws SQLException {
        // Récupérer les données de la base de données
        List<categorie> categories = sc.afficher();

        // Créer une ObservableList à partir des données
        ObservableList<categorie> categorieList = FXCollections.observableArrayList(categories);

        // Ajouter les données à la TableView
        tableviewcategorie.setItems(categorieList);
    }

    @FXML
    void AjouterCategorie(ActionEvent event) {
        if (NomC.getText().isEmpty()) {
            System.out.println("Please fill in all fields");
            // Display error message to user
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Veuillez remplir  le champs");
            errorAlert.showAndWait();
            return;
        }
        // Check if a category with the same name already exists
        try {
            boolean categoryExists = sc.checkCategoryExists(NomC.getText());
            if (categoryExists) {
                // Display error message to user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Une catégorie avec le même nom existe déjà");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., show an error message
            System.out.println(e.getMessage());
            return;
        }

        try {
            // Ajouter la catégorie avec le nom fourni
            sc.ajouter(new categorie(8,NomC.getText()));
            // Afficher un message de succès à l'utilisateur
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText("Catégorie ajoutée avec succès");
            successAlert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Handle the exception appropriately, e.g., show an error message
        }
    }

    @FXML
    void accederFormation(ActionEvent event) {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        try {
            Parent root = loader1.load();
            AjouterFormation controller = loader1.getController();
            NomC.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    void modifierCategorie(ActionEvent event) {
        // Récupérer la formation sélectionnée dans la TableView
        categorie categorieSelectionnee = tableviewcategorie.getSelectionModel().getSelectedItem();

        if (categorieSelectionnee != null) {
            // Afficher une boîte de dialogue de modification pour le champ nomFormation
            TextInputDialog dialog = new TextInputDialog(categorieSelectionnee.getNomCategorie());
            dialog.setTitle("Modifier Nom de categorie");
            dialog.setHeaderText(null);
            dialog.setContentText("Nouveau nom de formation :");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newNomFormation -> {
                categorieSelectionnee.setNomCategorie(newNomFormation);
                // Répercuter les changements dans la TableView
                tableviewcategorie.refresh();
                // Enregistrer les modifications dans la base de données
                try {
                    sc.modifier(categorieSelectionnee);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    void supprimerCategorie(ActionEvent event) throws SQLException {
        // Récupérer la formation sélectionnée dans la TableView
        categorie categorieSelectionnee = tableviewcategorie.getSelectionModel().getSelectedItem();

        // Supprimer la formation de la base de données et de la TableView
        if (categorieSelectionnee != null) {
            sc.supprimer(categorieSelectionnee);
            tableviewcategorie.getItems().remove(categorieSelectionnee);
        }
    }

    @FXML
    void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<categorie, Integer>("idCategorie"));
        nomc.setCellValueFactory(new PropertyValueFactory<categorie,String>("nomCategorie"));


    }

    public void salle(ActionEvent actionEvent) {
    }

    public void club(ActionEvent actionEvent) {
    }

    public void equipement(ActionEvent actionEvent) {
    }

    public void verspageadus(ActionEvent actionEvent) {
    }


    public void ajouter(ActionEvent actionEvent) {
    }
    public void quiz(ActionEvent actionEvent){

    }
    public void reclama(ActionEvent actionEvent){

    }
    public void event(ActionEvent actionEvent){

    }

    public void formations(ActionEvent actionEvent){

    }
    public void cours(ActionEvent actionEvent){

    }
    public void remise(ActionEvent actionEvent){

    }

}
