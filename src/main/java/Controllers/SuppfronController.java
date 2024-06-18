package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import Services.UserService;
public class SuppfronController {

    @FXML
    private TextField supprimerTF;
    private UserService userService = new UserService();

    @FXML
    void supprimerUser(ActionEvent event) {
        // Récupérer l'ID de l'utilisateur à partir du TextField
        String idUtilisateurStr = supprimerTF.getText();

        // Vérifier si l'ID est un entier valide
        try {
            int idUtilisateur = Integer.parseInt(idUtilisateurStr);

            // Appeler la méthode de service pour supprimer l'utilisateur par son ID
            userService.supprimer(idUtilisateur);
            System.out.println("L'utilisateur avec l'ID " + idUtilisateur + " a été supprimé avec succès.");
            // Vous pouvez ajouter ici des actions supplémentaires après la suppression de l'utilisateur
        } catch (NumberFormatException e) {
            System.err.println("L'ID de l'utilisateur doit être un entier valide.");
            // Gérer l'erreur si l'utilisateur entre un ID non valide (par exemple, afficher un message d'erreur à l'utilisateur)
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            // Gérer l'erreur (afficher un message à l'utilisateur, journaliser l'erreur, etc.)
        }

        // Afficher un message de confirmation à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setContentText("L'utilisateur a été supprimé avec succès.");
        alert.showAndWait();

    }

}