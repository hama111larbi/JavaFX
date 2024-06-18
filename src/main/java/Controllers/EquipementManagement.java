package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Services.ServiceEquipement;
import entities.equipement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;

public class EquipementManagement {

    @FXML
    private TextField qrcode;
    @FXML
    private TextField brand;
    @FXML
    private TextField nom;
    @FXML
    private TextField category;
    @FXML
    private TextField salle_id;
    @FXML
    private ListView<String> equiplistview;

    private ServiceEquipement SE = new ServiceEquipement();

    @FXML
    void salles(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    @FXML
    void ajouter(ActionEvent event) {
        if (salle_id == null || qrcode == null) {
            showAlert("Fields Missing", "Some fields are not injected.");
            return;
        }

        String categoryValue = category.getText();
        String salleIdText = salle_id.getText();
        String qrCodeText = qrcode.getText();

        if (salleIdText.isEmpty() || qrCodeText.isEmpty()) {
            showAlert("Fields Empty", "Please fill in all required fields.");
            return;
        }

        int salleId;
        try {
            salleId = Integer.parseInt(salleIdText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Salle ID", "Please enter a valid numeric Salle ID.");
            return;
        }

        if (!isValidEquip(categoryValue)) {
            showAlert("Invalid catégorie", "Please enter a valid catégorie (électronique, meuble, pédagogique, informatique).");
            return;
        }

        equipement nouvelEquipement = new equipement(0, salleId, nom.getText(), brand.getText(), categoryValue, qrCodeText);

        try {
            SE.ajouter(nouvelEquipement);
            showAlert("Success", "Equipement ajouté avec succès.");
            afficherDB(event); // Refresh the ListView after adding a new equipement
        } catch (SQLIntegrityConstraintViolationException e) {
            showAlert("Invalid Salle ID", "The provided Salle ID does not exist in the salle table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private boolean isValidEquip(String equipement) {
        String[] validEquipements = {"électronique", "meuble", "pédagogique", "informatique"};

        for (String validEquip : validEquipements) {
            if (validEquip.equalsIgnoreCase(equipement)) {
                return true;
            }
        }

        return false;
    }

    @FXML
    void modifier(ActionEvent event) {
        // Get the selected equipement object from the ListView
        String selectedItem = equiplistview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Parse the idequip from the selected item
            int idequip = parseIdFromSelectedItem(selectedItem);

            // Check if idequip is a valid integer
            if (idequip != -1) {
                try {
                    // Find the equipement from the list using the idequip
                    equipement equipementToModify = findEquipementById(idequip);

                    if (equipementToModify != null) {
                        // Retrieve the updated values from the TextFields
                        String newCategory = category.getText();
                        String newIdsalleText = salle_id.getText();
                        String newNom = nom.getText();
                        String newBrand = brand.getText();
                        String newQrCode = qrcode.getText();

                        // Check if idsalle is not empty
                        if (newIdsalleText.isEmpty()) {
                            showAlert("ID Salle Empty", "Please enter a valid ID Salle.");
                            return; // Exit the method if idsalle is empty
                        }

                        // Parse idsalle as int
                        int newIdsalleValue;
                        try {
                            newIdsalleValue = Integer.parseInt(newIdsalleText);
                        } catch (NumberFormatException e) {
                            showAlert("Invalid ID Salle", "Please enter a valid numeric ID Salle.");
                            return; // Exit the method if idsalle is not a valid integer
                        }

                        // Update the equipement object with new values
                        equipementToModify.setCategory(newCategory);
                        equipementToModify.setSalle_id(newIdsalleValue);
                        equipementToModify.setNom(newNom);
                        equipementToModify.setBrand(newBrand);
                        equipementToModify.setQr_code(newQrCode);

                        // Call the modifier method in ServiceEquipement to update the database
                        SE.modifier(equipementToModify);

                        // Refresh the ListView
                        afficherDB(event);
                    } else {
                        System.out.println("Equipement not found for idequip: " + idequip);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                }
            } else {
                System.out.println("Invalid idequip");
            }
        } else {
            System.out.println("No item selected.");
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        // Get the selected equipement object from the ListView
        String selectedItem = equiplistview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Parse the idequip from the selected item
            int idequip = parseIdFromSelectedItem(selectedItem);

            // Check if idequip is a valid integer
            if (idequip != -1) {
                try {
                    // Find the equipement from the list using the idequip
                    equipement equipementToDelete = findEquipementById(idequip);

                    if (equipementToDelete != null) {
                        // Delete the equipement from the database
                        SE.supprimer(equipementToDelete);

                        // Refresh the ListView
                        afficherDB(event);
                    } else {
                        System.out.println("Equipement not found for idequip: " + idequip);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                }
            } else {
                System.out.println("Invalid idequip");
            }
        } else {
            System.out.println("No item selected.");
        }
    }

    @FXML
    void afficherDB(ActionEvent event) {
        try {
            List<equipement> equipements = SE.afficher();

            // Clear any existing items in the ListView
            equiplistview.getItems().clear();

            // Add each equipement object to the ListView with a custom string representation
            for (equipement equip : equipements) {
                // Customize the string representation to exclude the id
                String displayString = "ID Equip: " + equip.getId() + ", Salle ID: " + equip.getSalle_id() + ", Nom: " + equip.getNom() + ", Brand: " + equip.getBrand() + ", Category: " + equip.getCategory() + ", QR Code: " + equip.getQr_code();

                System.out.println(equip);
                equiplistview.getItems().add(displayString);
            }
            System.out.println(equipements);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void initialize(URL location, ResourceBundle resources) {
        // Set a custom cell factory to display equipement objects in a more organized way
        equiplistview.setCellFactory(param -> new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // Assuming your equipement objects have a toString method
                    setText(item);
                }
            }
        });
    }

    private int parseIdFromSelectedItem(String selectedItem) {
        // Split the selected item string by comma
        String[] parts = selectedItem.split(",");

        // Iterate through each part to find the one containing "idequip"
        for (String part : parts) {
            if (part.trim().startsWith("ID Equip:")) {
                // Extract the idequip substring and parse it
                String idequipString = part.trim().substring("ID Equip:".length());
                try {
                    return Integer.parseInt(idequipString.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Handle the exception according to your needs
                    return -1; // or an appropriate default value
                }
            }
        }

        return -1; // If "idequip" is not found, return -1
    }


    private equipement findEquipementById(int idequip) {
        try {
            List<equipement> equipements = SE.afficher();
            for (equipement equip : equipements) {
                if (equip.getId() == idequip) {
                    return equip;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    // Helper method to validate categorie
    private boolean isValidCategorie(String categorie) {
        // List of valid categorie values
        String[] validCategories = {"électronique", "pédagogique", "meubles"};

        // Check if categorie is in the list of valid values
        for (String validCategorie : validCategories) {
            if (validCategorie.equalsIgnoreCase(categorie)) {
                return true;
            }
        }

        return false;
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

    public void cours(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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


