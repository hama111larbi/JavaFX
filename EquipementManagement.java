package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.ServiceEquipement;  // Change the service class
import entities.equipement;

import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.ListView;

public class EquipementManagement {

    public Button afficherDB;

    @FXML
    private TextField categorie;

    @FXML
    private TextField idsalle;

    @FXML
    private ListView<String>  equiplistview;

    private ServiceEquipement SE = new ServiceEquipement();  // Change the service class

    private equipement selectedEquipement; // Store the selected equipement

    @FXML
    void ajouter(ActionEvent event) {
        // Check if idsalle is injected
        if (idsalle == null) {
            showAlert("ID Salle Field Missing", "ID Salle field is not injected.");
            return;
        }

        // Retrieve values from the TextFields
        String categorieValue = categorie.getText();
        String idsalleValueText = idsalle.getText();

        // Check if idsalle is not empty
        if (idsalleValueText.isEmpty()) {
            showAlert("ID Salle Empty", "Please enter a valid ID Salle.");
            return; // Exit the method if idsalle is empty
        }

        // Parse idsalle as int
        int idsalleValue;
        try {
            idsalleValue = Integer.parseInt(idsalleValueText);
        } catch (NumberFormatException e) {
            showAlert("Invalid ID Salle", "Please enter a valid numeric ID Salle.");
            return; // Exit the method if idsalle is not a valid integer
        }

        // Create a new equipement object
        equipement nouvelEquipement = new equipement(0, idsalleValue, categorieValue); // 0 is a placeholder for idequip

        try {
            // Add the new equipement to the database using ServiceEquipement
            SE.ajouter(nouvelEquipement);

            // You may add additional logic here based on your requirements

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
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
                        String newCategorie = categorie.getText();
                        String newIdsalleText = idsalle.getText();

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
                        equipementToModify.setCategorie(newCategorie);
                        equipementToModify.setIdsalle(newIdsalleValue);

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

    // Helper method to find equipement by idequip
    private equipement findEquipementById(int idequip) {
        try {
            List<equipement> equipements = SE.afficher();
            for (equipement equip : equipements) {
                if (equip.getIdequip() == idequip) {
                    return equip;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    // Helper method to parse the idequip from the string representation of an equipement object
    private int parseIdFromSelectedItem(String selectedItem) {
        // Assuming your string representation is in the format "equipement{idequip=<idequip>, ...}"
        int startIndex = selectedItem.indexOf("idequip=") + "idequip=".length();
        int endIndex = selectedItem.indexOf(",", startIndex);
        return Integer.parseInt(selectedItem.substring(startIndex, endIndex));
    }

    // Method to set the selected equipement
    public void setSelectedEquipement(equipement selectedEquipement) {
        this.selectedEquipement = selectedEquipement;
    }

    // Helper method to show an alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void afficherDB(ActionEvent event) {
        try {
            List<equipement> equipements = SE.afficher();

            // Clear any existing items in the ListView
            equiplistview.getItems().clear();

            // Add each equipement object to the ListView
            for (equipement equip : equipements) {
                System.out.println(equip);
                equiplistview.getItems().add(equip.toString());
            }
            System.out.println(equipements);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void salles(ActionEvent actionEvent) {
    }
}
