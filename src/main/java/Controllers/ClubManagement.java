package Controllers;


import javax.mail.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Club;
import Services.ClubService;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class ClubManagement {

    public ListView clubListView;
    @FXML
    private Button ajouter;

    @FXML
    private Button supprimer;

    @FXML
    private Button modifier;

    @FXML
    private Button afficher;

    @FXML
    private TextField searchTextField;
    @FXML
    void event(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }



    @FXML
    private TextField id;

    @FXML
    private TextField iduser;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField numtlf;

    private ClubService clubService;

    public ClubManagement() {
        clubService = new ClubService();
    }

    @FXML
    void ajouter(ActionEvent event) {
        String nameValue = name.getText();
        String emailValue = email.getText();
        String idUserValue = iduser.getText();
        String numTlfValue = numtlf.getText();

        // Vérifier si tous les attributs sont remplis
        if (nameValue.isEmpty() || emailValue.isEmpty() || idUserValue.isEmpty() || numTlfValue.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si le numéro de téléphone est composé de 8 chiffres
        if (!numTlfValue.matches("\\d{8}")) {
            showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres.");
            return;
        }

        // Vérifier si l'adresse email contient le symbole "@"
        if (!emailValue.contains("@")) {
            showAlert("Erreur", "L'adresse email doit contenir le symbole '@'.");
            return;
        }

        // Vérifier si la première lettre du nom du club est majuscule dans l'intervalle [A..Z]
        if (!Character.isUpperCase(nameValue.charAt(0))) {
            showAlert("Erreur", "Le nom du club doit commencer par une majuscule.");
            return;
        }

        // Convertir iduser et numtlf en entiers
        int userIdValue = Integer.parseInt(idUserValue);
        int numTlfIntValue = Integer.parseInt(numTlfValue);

        // Créer un nouveau club avec les valeurs saisies
        Club newClub = new Club(userIdValue, nameValue, emailValue, numTlfIntValue);

        try {
            // Ajouter le nouveau club à la base de données
            clubService.add(newClub);
            sendConfirmationEmail(String.valueOf(email));
            showAlert("Club ajouté", "Le club a été ajouté avec succès.");
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du club: " + e.getMessage());
        }
    }


    @FXML
    void modifier(ActionEvent event) {
        // Récupérer le club sélectionné dans la ListView
        String selectedItem = (String) clubListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Analyser l'ID à partir de l'élément sélectionné
            int clubId = parseIdFromSelectedItem(selectedItem);
            // Vérifier si l'ID du club est un entier valide
            if (clubId != -1) {
                try {
                    // Trouver le club dans la liste des clubs
                    Club clubToUpdate = findClubById(clubId);
                    if (clubToUpdate != null) {
                        // Mettre à jour les champs du club avec les nouvelles valeurs
                        clubToUpdate.setNameclub(name.getText());
                        clubToUpdate.setEmail(email.getText());
                        clubToUpdate.setNumtlf(Integer.parseInt(numtlf.getText()));

                        // Appeler la méthode update du ClubService pour mettre à jour le club dans la base de données
                        clubService.update(clubToUpdate);

                        // Actualiser la ListView pour refléter les modifications
                        afficher(event);

                        showAlert("Club modifié", "Le club a été modifié avec succès.");
                        clearFields();
                    } else {
                        System.out.println("Club introuvable pour l'ID: " + clubId);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer l'exception de manière appropriée
                }
            } else {
                System.out.println("ID de club invalide.");
            }
        } else {
            System.out.println("Aucun élément sélectionné.");
        }
    }


    @FXML
    void supprimer(ActionEvent event) {
        // Récupérer le club sélectionné dans la ListView
        String selectedItem = (String) clubListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Analyser l'ID à partir de l'élément sélectionné
            int clubId = parseIdFromSelectedItem(selectedItem);
            // Vérifier si clubId est un entier valide
            if (clubId != -1) {
                try {
                    // Trouver le club à partir de la liste en utilisant clubId
                    Club clubToDelete = findClubById(clubId);
                    if (clubToDelete != null) {
                        // Supprimer le club de la base de données
                        clubService.delete(clubToDelete);

                        // Actualiser la ListView
                        afficher(event);
                    } else {
                        System.out.println("Club introuvable pour l'ID: " + clubId);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer l'exception de manière appropriée
                }
            } else {
                System.out.println("ID de club invalide.");
            }
        } else {
            System.out.println("Aucun élément sélectionné.");
        }
    }




    @FXML
    void afficher(ActionEvent event) {
        try {
            List<Club> clubs = clubService.afficher();
            clubListView.getItems().clear();
            for (Club club : clubs) {
                String displayString = "ID: " + club.getIdclub() + ", Nom: " + club.getNameclub() + ", Email: " + club.getEmail() + ", Numéro de téléphone: " + club.getNumtlf();
                clubListView.getItems().add(displayString);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'affichage des clubs: " + e.getMessage());
        }
    }

    private void clearFields() {
        name.clear();
        email.clear();
        iduser.clear();
        numtlf.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private Club findClubById(int clubId) {
        try {
            List<Club> clubs = clubService.afficher();
            for (Club club : clubs) {
                if (club.getIdclub() == clubId) {
                    return club;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception de manière appropriée
        }
        return null;
    }

    private int parseIdFromSelectedItem(String selectedItem) {
        try {
            // Split the selectedItem string to extract the part containing the ID
            String[] parts = selectedItem.split(",");

            // Iterate through the parts to find the one containing the ID
            for (String part : parts) {
                if (part.trim().startsWith("ID:")) {
                    // Extract the substring containing the ID
                    String idString = part.trim().substring("ID:".length());

                    // Convert the extracted substring to an integer
                    return Integer.parseInt(idString.trim());
                }
            }

            // Handle the case where ID is not found in the selected item
            throw new IllegalArgumentException("ID not found in the selected item.");
        } catch (NumberFormatException e) {
            // Handle NumberFormatException, e.g., log the error or show an alert
            e.printStackTrace();
            return -1; // Return a default value or handle appropriately
        } catch (IllegalArgumentException e) {
            // Handle the case where ID is not found in the selected item
            e.printStackTrace();
            return -1; // Return a default value or handle appropriately
        }
    }

    @FXML
    void search(ActionEvent actionEvent) {

        String searchTerm = searchTextField.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()) {
            afficher(actionEvent);  // Utilisez votre méthode d'affichage par défaut pour restaurer les données initiales
            return;
        }

        ObservableList<String> allSalleStrings = clubListView.getItems();
        ObservableList<String> searchResults = FXCollections.observableArrayList();

        for (String salleString : allSalleStrings) {
            if (salleString.toLowerCase().contains(searchTerm)) {
                searchResults.add(salleString);
            }
        }

        clubListView.getItems().clear();
        clubListView.setItems(searchResults);
    }
    @FXML
    void sendConfirmationEmail(String s) {
        String recipientEmail = email.getText().trim(); // Extract the email address from the TextField

        // Check if the email address is empty
        if (recipientEmail.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir une adresse e-mail.");
            return;
        }

        // Configure the mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Configure the sending account
        String senderEmail = "cherif.benhassine@esprit.tn";
        String password = "Achraf2006+"; // Replace "actual_password" with the real password

        // Create a mail session with authentication
        Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            // Create a message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail.trim())); // Trim the recipient email address
            message.setSubject("Confirmation de réservation");
            message.setText("bienvenu avec nous .");

            // Send the message
            Transport.send(message);
            System.out.println("E-mail de confirmation envoyé avec succès !");

            // Show an alert dialog
            showAlert("Confirmation", "E-mail de confirmation envoyé avec succès !");
        } catch (MessagingException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
            // Show an alert dialog in case of an error
            showAlert("Erreur", "Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
        }
    }



    @FXML
    void sort(ActionEvent actionEvent) {
        try {
            // Récupérer la liste des clubs depuis le service
            List<Club> clubs = clubService.afficher();

            // Trier les clubs par nom
            clubs.sort(Comparator.comparing(Club::getNameclub));

            // Effacer les éléments actuels de la ListView
            clubListView.getItems().clear();

            // Ajouter les clubs triés à la ListView
            for (Club club : clubs) {
                String displayString = "ID: " + club.getIdclub() + ", Nom: " + club.getNameclub() + ", Email: " + club.getEmail() + ", Numéro de téléphone: " + club.getNumtlf();
                clubListView.getItems().add(displayString);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite lors du tri des clubs: " + e.getMessage());
        }
    }



    public void numtlf(ActionEvent actionEvent) {
    }

    public void iduser(ActionEvent actionEvent) {
    }

    public void email(ActionEvent actionEvent) {
    }

    public void nameclub(ActionEvent actionEvent) {
    }

    public void quiz(ActionEvent actionEvent) {
    }

    public void reclama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
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

   // public void salle(ActionEvent actionEvent) {}

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

    public void verspageadus(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
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
