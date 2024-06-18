package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import models.User;
import Services.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

public class inscritController implements Initializable {
    private ToggleGroup toggleGroup;
    private RadioButton selectedRadioButton;

    private UserService userService = new UserService();

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField adresseTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField mdpTF;

    @FXML
    private RadioButton et;

    @FXML
    private RadioButton fo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        et.setToggleGroup(toggleGroup);
        fo.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            selectedRadioButton = (RadioButton) newValue;
        });
    }

    @FXML
    void ajouterUser(ActionEvent event) throws SQLException {
        // Validate inputs
        if (validateInputs()) {
            UserService userService = new UserService();
            User user = new User();
            user.setNom(nomTF.getText());
            user.setPrenom(prenomTF.getText());
            user.setAdresse(adresseTF.getText());
            user.setEmail(emailTF.getText());

            // Hash the password
            String hashedPassword = BCrypt.hashpw(mdpTF.getText(), BCrypt.gensalt());
            user.setMdp(hashedPassword);

            user.setrole(selectedRadioButton.getText());
            try {
                userService.ajouter(user);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Personne Ajouter");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Check if fields are empty
        if (nomTF.getText().isEmpty()) {
            showAlert("Erreur", "Champs nom vide");
            isValid = false;
        }
        if (prenomTF.getText().isEmpty()) {
            showAlert("Erreur", "Champs prenom vide");
            isValid = false;
        }
        if (emailTF.getText().isEmpty()) {
            showAlert("Erreur", "Champs email vide");
            isValid = false;
        } else if (!emailTF.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Erreur", "Email pas valide");
            isValid = false;
        }

        if (mdpTF.getText().isEmpty()) {
            showAlert("Erreur", "Champs vide");
            isValid = false;
       /* } else if (mdpTF.getText().length()) {
            showAlert("Erreur", "Le mot de passe doit comporter au moins 8 caractères.");
            isValid = false;*/
        } else if (!mdpTF.getText().matches(".*\\d.*")) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins un chiffre.");
            isValid = false;
        }

        return isValid;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}