package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.cours;
import models.formation;
import Services.servicecours;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Coursuser {

    @FXML
    private VBox affichagecoursvbox;

    private final servicecours SC = new servicecours();
    private int formationId;

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

    @FXML

    void affichercours(ActionEvent event) {
        try {
            List<cours> courss = SC.afficher();

            // Clear any existing items in the VBox
            affichagecoursvbox.getChildren().clear();

            // Loop through each Cours object and add its details to the VBox
            for (int i = 0; i < courss.size(); i += 4) {
                // Create a new HBox for each row
                HBox rowBox = new HBox();
                rowBox.setSpacing(20); // Adjust spacing between cours in a row

                // Loop through 4 cours and add them to the current row
                for (int j = i; j < Math.min(i + 4, courss.size()); j++) {
                    cours cours = courss.get(j);

                        // Create labels for each property of the Cours object
                    Hyperlink pdfLink = new Hyperlink("Description");
                    pdfLink.setOnAction(e -> {
                        // Save the PDF file to a temporary location and open it
                        File pdfFile = savePdfToFile(cours.getDescription_Cours());
                        if (pdfFile != null) {
                            // Open the file
                            // This part depends on your application's file handling
                        }
                    });

                    VBox coursBox = new VBox(
                            new Label("Nom de cours: " + cours.getNom_Cours()),
                            pdfLink
                    );
                    coursBox.setSpacing(5); // Adjust spacing between labels in a cours

                    // Add the VBox for the current cours to the row
                    rowBox.getChildren().add(coursBox);
                }

                // Add the current row to the VBox
                affichagecoursvbox.getChildren().add(rowBox);

                // Add spacing between rows
                Region spacer = new Region();
                spacer.setPrefHeight(40); // Adjust the height to increase or decrease the spacing between rows
                affichagecoursvbox.getChildren().add(spacer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private File savePdfToFile(byte[] pdfData) {
        // Choisissez un répertoire où votre application a les autorisations d'écriture
        String directoryPath = "C:\\Users\\HP\\IdeaProjects\\test_final\\pdf";
        String filePath = directoryPath + File.separator + "cours1.pdf"; // Chemin du fichier PDF de destination
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Crée le répertoire s'il n'existe pas
            }
            File pdfFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(pdfData);
            }
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}



