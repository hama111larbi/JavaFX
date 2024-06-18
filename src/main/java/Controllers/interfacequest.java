package Controllers;

import Services.questionservice;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Questions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class interfacequest {

    ItemRecController irc=new ItemRecController();
    // Vos autres champs et méthodes existants



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private HBox item;
    @FXML
    private VBox pnitems;

    public void setM(Label m) {
        this.m = m;
    }

    public Label getM() {
        return m;
    }

    private Label m;
    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

private int n;
    questionservice rs = new questionservice();


    private void handleButtonPress(Button button, String selectedOption, String correctAnswer) {
        if (selectedOption.equals(correctAnswer)) {
            System.out.println("Bonne réponse : " + button.getText());
        } else {
            System.out.println("Mauvaise réponse : " + button.getText());
        }
    }
    // Initialisation du SecurityManager
private int id;

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    void initialize() throws IOException {
     System.out.println("idquest"+id);
        //System.out.print ln(getN());

        ObservableList<Questions> R = null;
        try {
            int k=n;
            R = rs.get(k);


            for (int i = 0; i < R.size(); i++) {

                final int j = i;
                Questions q = R.get(j);
               // q.setIdquiz(n);
                // System.out.println(getId());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemquest.fxml"));
                Node node = loader.load();


                ((Label) node.lookup("#quest")).setText(q.getQuestion());
                ((Button) node.lookup("#op1")).setText(q.getOp1());
                ((Button) node.lookup("#op2")).setText(q.getOp2());
                ((Button) node.lookup("#op3")).setText(q.getOp3());
                pnitems.getChildren().add(node);



                Button button1 = (Button) node.lookup("#op1");
                Button button2 = (Button) node.lookup("#op2");
                Button button3 = (Button) node.lookup("#op3");

                button1.setOnAction(event -> handleButtonPress(button1, q.getOp1(), q.getAnswer()));
                button2.setOnAction(event -> handleButtonPress(button2, q.getOp2(), q.getAnswer()));
                button3.setOnAction(event -> handleButtonPress(button3, q.getOp3(), q.getAnswer()));


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void translate(ActionEvent event) throws IOException, InterruptedException {


    }


    public void yraja3(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter le quiz ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière une fois que vous aurez quitté.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a confirmé qu'il souhaite quitter le quiz
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
            Parent root = loader.load();
            Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            st.setScene(scene);
            st.show();
        } else {
            // L'utilisateur a annulé la fermeture de la fenêtre
            actionEvent.consume();
        }
    }}



