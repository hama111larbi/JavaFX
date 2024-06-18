package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.User;
import test.HelloApplication;

import java.io.IOException;
import java.util.Optional;

public class PageUser {

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    public void setUser(User user) {
        if (user != null) {
            labelNom.setText(user.getNom());
            labelPrenom.setText(user.getPrenom());
        }
    }

    public void setNom(String nom) {
        labelNom.setText(nom);
    }

    // Méthode pour mettre à jour le prénom de l'utilisateur dans l'interface utilisateur
    public void setPrenom(String prenom) {
        labelPrenom.setText(prenom);
    }

    public void Modification(ActionEvent actionEvent) {
    }

    public void VersSupprimer(ActionEvent actionEvent) {
    }

    public void quiz(ActionEvent actionEvent) {
    }

    public void ajouter(ActionEvent actionEvent) {
    }

    public void reclama(ActionEvent actionEvent) {
    }

    public void event(ActionEvent actionEvent) {
    }

    public void formations(ActionEvent actionEvent) {
    }

    public void cours(ActionEvent actionEvent) {
    }

    public void remise(ActionEvent actionEvent) {
    }

    public void salle(ActionEvent actionEvent) {
    }

    public void club(ActionEvent actionEvent) {
    }

    public void equipement(ActionEvent actionEvent) {
    }

    public void verspageadus(ActionEvent actionEvent) {
    }
}
