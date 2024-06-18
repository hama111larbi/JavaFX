package Controllers;

import Services.quizservice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableList;

public class quiz implements Initializable {
public int sc;
   private static quizservice rs=new quizservice();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField find;


    @FXML
    private VBox pnitems;

    public VBox getPnitems() {
        return pnitems;
    }

    public void setPnitems(VBox pnitems) {
        this.pnitems = pnitems;
    }
    public boolean quizCompleted = false;
    @FXML
    private Label lancer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList<models.quiz> R = null;
        try {
            R = rs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader loader = null;
        for (int i = 0; i < R.size(); i++) {
            try {
                final int j = i;
                models.quiz quiz = R.get(j);
                loader = new FXMLLoader(getClass().getResource("/itemRec.fxml"));
                Node node = loader.load();
                System.out.println(quiz.getIdquiz());

                ((Label) node.lookup("#nom")).setText(quiz.getNom());
                ((Label) node.lookup("#id")).setText(String.valueOf(quiz.getIdquiz()));
                System.out.println();
                if (quizCompleted) {
                    ((Button) node.lookup("#Start")).setDisable(true);
                }

                // Give the items some effect
                node.setOnMouseEntered(event -> {
                    node.setStyle("-fx-background-color : #ffffff");
                });
                node.setOnMouseExited(event -> {
                    node.setStyle("-fx-background-color : #ffffff");
                });

                pnitems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            search();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        void search() throws SQLException {
            ObservableList<models.quiz> listquiz = rs.getAll();
            ObservableList<models.quiz> observableList = observableList(listquiz);
            FilteredList<models.quiz> filteredData = new FilteredList<>(observableList, p -> true);

            find.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(quiz -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return String.valueOf(quiz.getNom()).toLowerCase().contains(lowerCaseFilter);
                });

                // Clear previous items in the UI
                pnitems.getChildren().clear();

                // Update UI with the filtered data
                for (models.quiz qz  : filteredData) {
                    // Initialize node for each item
                    Node node = null; // Update this line based on your actual FXML structure
                    try {
                        node = FXMLLoader.load(getClass().getResource("/itemRec.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (node != null) {
                        ((Label) node.lookup("#nom")).setText(qz.getNom());
                        // Update other fields as needed
                        pnitems.getChildren().add(node); // Add the node to the UI
                    }
                }
            });
        }


    public void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }

    public void quiz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }

    public void envoyer(ActionEvent event) throws IOException {
        System.out.println(sc);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resultat");
        alert.setHeaderText("Vous avez obtenu "+sc);
       // alert.setContentText("Vous ne pourrez pas revenir en arrière une fois que vous aurez quitté.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void dispo(ActionEvent event) {
    }


    public void emplacement(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();


    }
    public void salles(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }

    public void formation(ActionEvent event) {
    }
    public void event(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface etudiant.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void profil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
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

