package Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import models.User;
import Services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class AfficherUsers {

    @FXML
    private TextField nomNouv;

    @FXML
    private TextField prenomNouv;

    @FXML
    private TextField adresseNouv;

    @FXML
    private TextField emailNouv;

    @FXML
    private TextField mdpNouv;

    @FXML
    private RadioButton et;

    @FXML
    private RadioButton fo;

    @FXML
    private RadioButton ad;

    @FXML
    private ListView<User> affiche;

    private ToggleGroup toggleGroup;

    private UserService userService = new UserService();

    public void affichage() {
        try {
            // Récupérer la liste des utilisateurs depuis le service
            ObservableList<User> us = userService.recupperer();
            affiche.setItems(us);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void PageSupprimer() {
        // Récupérer l'utilisateur sélectionné dans la liste
        User selectedUser = affiche.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                // Supprimer l'utilisateur sélectionné depuis la base de données
                userService.supprimer(selectedUser.getId());
                // Rafraîchir la liste des utilisateurs affichée
                affichage();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void ajouteruser() {
        // Récupérer les valeurs saisies dans les champs de texte et les boutons radio
        String nouveauNom = nomNouv.getText();
        String nouveauPrenom = prenomNouv.getText();
        String nouvelleAdresse = adresseNouv.getText();
        String nouveauEmail = emailNouv.getText();
        String nouveauMdp = mdpNouv.getText();
        String role = "";
        if (et.isSelected()) {
            role = et.getText();
        } else if (fo.isSelected()) {
            role = fo.getText();
        } else if (ad.isSelected()) {
            role = ad.getText();
        }

        // Crypter le mot de passe avec jBCrypt
        String motDePasseCrypte = BCrypt.hashpw(nouveauMdp, BCrypt.gensalt());

        // Créer un nouvel utilisateur avec les valeurs saisies
        User newUser = new User(nouveauNom, nouveauPrenom, nouvelleAdresse, nouveauEmail, motDePasseCrypte, role);

        try {
            // Ajouter l'utilisateur à la base de données
            userService.ajouter(newUser);
            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Personne Ajoutée");
            alert.showAndWait();
            // Rafraîchir la liste des utilisateurs affichée
            affichage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /* @FXML
     void ajouteruser() {
         // Récupérer les valeurs saisies dans les champs de texte et les boutons radio
         String nouveauNom = nomNouv.getText();
         String nouveauPrenom = prenomNouv.getText();
         String nouvelleAdresse = adresseNouv.getText();
         String nouveauEmail = emailNouv.getText();
         String nouveauMdp = mdpNouv.getText();
         String role = "";
         if (et.isSelected()) {
             role = et.getText();
         } else if (fo.isSelected()) {
             role = fo.getText();
         } else if (ad.isSelected()) {
             role = ad.getText();
         }

         // Créer un nouvel utilisateur avec les valeurs saisies
         User newUser = new User(nouveauNom, nouveauPrenom, nouvelleAdresse, nouveauEmail, nouveauMdp, role);

         try {
             // Ajouter l'utilisateur à la base de données
             userService.ajouter(newUser);
             // Afficher un message de confirmation
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Success");
             alert.setContentText("Personne Ajoutée");
             alert.showAndWait();
             // Rafraîchir la liste des utilisateurs affichée
             affichage();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 */
    @FXML
    void initialize() {
        affichage();
    }

    public void selecter() {
        User selectedUser = affiche.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Afficher les informations de l'utilisateur sélectionné dans les champs de texte
            nomNouv.setText(selectedUser.getNom());
            prenomNouv.setText(selectedUser.getPrenom());
            adresseNouv.setText(selectedUser.getAdresse());
            emailNouv.setText(selectedUser.getEmail());
            mdpNouv.setText(selectedUser.getMdp());
        }
    }





    public void auth(ActionEvent actionEvent) {
    }

    public void PageModifier(ActionEvent actionEvent) {
        User selectedUser = affiche.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Mettre à jour les informations de l'utilisateur sélectionné avec les nouvelles valeurs
            selectedUser.setNom(nomNouv.getText());
            selectedUser.setPrenom(prenomNouv.getText());
            selectedUser.setAdresse(adresseNouv.getText());
            selectedUser.setEmail(emailNouv.getText());

            // Crypter le nouveau mot de passe s'il a été modifié
            String nouveauMdp = mdpNouv.getText();
            if (!nouveauMdp.isEmpty()) {
                String motDePasseCrypte = BCrypt.hashpw(nouveauMdp, BCrypt.gensalt());
                selectedUser.setMdp(motDePasseCrypte);
            }

            // Appeler la méthode de votre service pour modifier l'utilisateur dans la base de données
            try {
                userService.modifier(selectedUser);
                // Rafraîchir la liste des utilisateurs affichée
                affichage();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public void ajouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affiche.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }


    public void verspageadus(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }



    public void quiz(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/questions1.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void reclama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void formations(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void cours(ActionEvent actionEvent) {
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


    public void pai(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }
}