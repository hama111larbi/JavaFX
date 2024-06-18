package Controller;






import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.YouTubeAPIExample;
import models.cours;
import Services.servicecours;
import Services.serviceFormation;

public class CoursApprenant {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private HBox affichagecoursvbox;

        @FXML
        private VBox afficherpdf;

        @FXML
        private Label nomCategorie;

        @FXML
        private Hyperlink pdfLink;
    @FXML
    private StackPane centerPane;



        private servicecours serviceC = new servicecours();
        private serviceFormation serviceFormation = new serviceFormation();
        @FXML
        void AfficherCours(ActionEvent event) {
            try {
                // Clear the existing content in the VBox
                affichagecoursvbox.getChildren().clear();

                // Retrieve the list of courses from the database
                List<cours> coursList = serviceC.afficher();

                if (coursList.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Information", "No Courses", "There are no courses to display.");
                    return;
                }

                // Loop through the list of courses
                for (cours cours : coursList) {
                    // Create labels to display course details
                    Label coursLabel = new Label("Nom: " + cours.getNom_Cours());

                    // Decode the description from byte array to string
                    String description = new String(cours.getDescription_Cours());
                    Label descriptionLabel = new Label("Description: " + description);

//                // Create a WebView to display the PDF content
//                WebView courseWebView = new WebView();
//                courseWebView.setPrefSize(800, 600); // Set WebView size
//                loadPDFContent(cours.getDescription_Cours(), courseWebView);
                    // Create a Hyperlink to download/open the PDF
                    Hyperlink pdfLink = new Hyperlink("Download PDF");
                    pdfLink.setOnAction(e -> {
                        // Handle the action to download/open the PDF
                        try {
                            // Logic to download the PDF file associated with the selected course
                            String fileName = cours.getNom_Cours() + ".pdf"; // Construct the file name
                            byte[] pdfData = cours.getDescription_Cours(); // Get the PDF data from the course
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Save PDF File");
                            fileChooser.setInitialFileName(fileName);
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                            fileChooser.getExtensionFilters().add(extFilter);
                            File file = fileChooser.showSaveDialog(new Stage());
                            if (file != null) {
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    fos.write(pdfData); // Write the PDF data to the file
                                    showAlert(Alert.AlertType.INFORMATION, "Download Successful", "PDF downloaded successfully", "File saved as: " + file.getAbsolutePath());
                                } catch (IOException ex) {
                                    showAlert(Alert.AlertType.ERROR, "Error", "File Save Error", "An error occurred while saving the PDF file: " + ex.getMessage());
                                }
                            }
                        } catch (Exception ex) {
                            showAlert(Alert.AlertType.ERROR, "Error", "PDF Download Error", "An error occurred while downloading the PDF: " + ex.getMessage());
                        }
                    });

                    // Optionally, you can add an image to represent the course
                    ImageView imageView = new ImageView(new Image("/src/cours.png"));
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);

                    // Create a VBox to hold course details and WebView
                    VBox courseBox = new VBox(imageView, coursLabel, pdfLink);
                    courseBox.setSpacing(5); // Adjust spacing between elements
                    // Add the courseBox to the main VBox
                    affichagecoursvbox.getChildren().add(courseBox);
                }
            } catch (SQLException e) {
                // Handle the exception appropriately
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while retrieving courses from the database.");
            }
        }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();

    }
    @FXML
    private void initialize() {
        // Call the method to display courses when the controller is initialized
        AfficherCours(null);

    }
    @FXML
    void consultervideo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/youtube_viewer.fxml"));

        Parent root = loader.load();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);


        YouTubeAPIExample controller = loader.getController();

    }



    }




