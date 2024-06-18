
package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;
import Services.UserService;
import test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class login {

    @FXML
    private TextField LogInEmail;

    @FXML
    private TextField LogInMdp;


    @FXML
    void verspageadus(ActionEvent event) throws SQLException {
        // Récupérer l'email et le mot de passe entrés par l'utilisateur
        String email = LogInEmail.getText();
        String mdp = LogInMdp.getText();


        // Vérifier si l'email et le mot de passe correspondent à l'un des Admin
        if ((email.equals("eyanasfi@esprit.tn") && mdp.equals("eya")) ||
                (email.equals("khouloudabid@gmail.com") && mdp.equals("khouloud")) ||
                (email.equals("marwanhamedadmin@gmail.com") && mdp.equals("marwanhamedadmin")) ||
                (email.equals("kossayboubakeradmin@gmail.com") && mdp.equals("kossayboubakeradmin")) ||
                (email.equals("amirbargouguiadmin@gmail.com") && mdp.equals("amirbargouguiadmin"))) {
            // System.out.println( Integer.parseInt(LogInEmail.getId()));
            // Naviguer vers la page de l'administrateur (AfficherUsers.fxml)
            navigateToPage("/AfficherUsers.fxml", event);
        } else if ((email.equals("khouloud@esprit.tn") && mdp.equals("khouloud")) ||
                (email.equals("formateur@gmail.com") && mdp.equals("123456789"))) {
            // System.out.println( Integer.parseInt(LogInEmail.getId()));
            System.out.println();
            // Naviguer vers l'interface front (front.fxml)
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/front.fxml"));
            try {
                LogInEmail.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Sinon, vérifier si les informations d'identification correspondent à un utilisateur dans la base de données
            UserService userService = new UserService();
            try {
                User user = userService.authentifier(email, mdp);
                if (user != null) {
                    // Vérifier le rôle de l'utilisateur
                    String role = user.getrole(); // Using the correct method name
                    if (role.equals("etudiant") || role.equals("formateur")) {
                        // Redirect to the front interface
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/front.fxml"));
                        try {
                            LogInEmail.getScene().setRoot(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        // Naviguer vers l'interface utilisateur (PageUser.fxml) en passant les informations de l'utilisateur
                        navigateToPageUser(event, user);
                    }
                } else {
                    afficherErreur("Erreur", "Email ou mot de passe incorrect.");
                }
            } catch (SQLException e) {
                afficherErreur("Erreur", "Erreur lors de la connexion : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /*
@FXML
void verspageadus(ActionEvent event)  {
    // Récupérer l'email et le mot de passe entrés par l'utilisateur
    String email = LogInEmail.getText();
    String mdp = LogInMdp.getText();

    // Vérifier si l'email et le mot de passe correspondent à l'un des Admin
    if ((email.equals("azerbenamoradmin@gmail.com") && mdp.equals("azeradmin")) ||
            (email.equals("nessimayadiadmin@gmail.com") && mdp.equals("nessimayadiadmin")) ||
            (email.equals("marwanhamedadmin@gmail.com") && mdp.equals("marwanhamedadmin")) ||
            (email.equals("kossayboubakeradmin@gmail.com") && mdp.equals("kossayboubakeradmin")) ||
            (email.equals("amirbargouguiadmin@gmail.com") && mdp.equals("amirbargouguiadmin"))) {
        // Naviguer vers la page de l'administrateur (PageAdmin.fxml)
        navigateToPage("/tn/esprit/crud/AfficherUsers.fxml", event);
    } else {
        // Sinon, vérifier si les informations d'identification correspondent à un utilisateur dans la base de données
        UserService userService = new UserService();
        try {
            User user = userService.authentifier(email, mdp);
            if (((email.equals("salih@gmail.com") && mdp.equals("123456789"))))
                   {
                // Naviguer vers la page utilisateur (PageUser.fxml) en passant les informations de l'utilisateur
                navigateToPageUser("/tn/esprit/crud/front.fxml", event, user);
            }
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de la connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/

    private void navigateToPageUser(ActionEvent event, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
            Parent root = loader.load();
            PageUser controller = loader.getController();
            controller.setNom(user.getNom());
            controller.setPrenom(user.getPrenom());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void navigateToPage(String s, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) LogInEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void ToForgetPass(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/MdpOublier.fxml"));
        try {
            LogInMdp.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void inscription(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/inscription.fxml"));
        try {
            LogInMdp.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }



}