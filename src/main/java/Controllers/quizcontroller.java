package Controllers;

import Services.questionservice;
import Services.quizservice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.quiz;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class quizcontroller {

    private Stage stage;
    private Scene scene;
    private int lastInsertedId; // Ajoutez cette variable pour stocker l'ID du dernier quiz inséré

    @FXML
    private TableView<quiz> affiche;
    @FXML
    private TableColumn<quiz, String> nomtab;
    private int id; // Variable pour stocker l'ID à transmettre

    // Méthode pour initialiser l'ID
    public void setId(int id) {
        this.id = id;
    }
    private Parent root;
    quizservice qs=new quizservice() {


    };
    questionservice qss=new questionservice();

    @FXML
    private TextField nom;
    private ToggleGroup radioGroup;
    public void select() throws SQLException {


        ///int num = affiche.getSelectionModel().getSelectedIndex();
        quiz q = affiche.getItems().get(affiche.getSelectionModel().getSelectedIndex());
        nom.setText(q.getNom());
    }

    private void affichage() throws SQLException {
        //quiz quiz=new quiz();
        ObservableList<quiz> qui = qs.getAll();
        affiche.setItems(qui);

        nomtab.setCellValueFactory(new PropertyValueFactory<quiz, String>("nom"));

    }
    @FXML
    void initialize() throws SQLException {affiche.setOnMouseClicked(event -> {
        try {
            select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });
    affichage();
        //affiche.setItems(FXCollections.observableArrayList());
        affiche.getItems();

    }


    public void ajout(ActionEvent actionEvent) throws SQLException, IOException {
        String t = nom.getText();
                if (!(t.trim().isEmpty() )) {
                    quiz qz = new quiz(t);
                    qs.add1(qz);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("failed");
                    alert.setContentText("ce nom est vide ");
                    alert.showAndWait();
                    //System.out.println("nom existe ");
                }


        try {
            affichage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // FXMLLoader loader = null;

        }

    @FXML
    void passer(ActionEvent event) throws IOException, SQLException {
        if (!selectyyy()) {
            affiche.getSelectionModel().getSelectedIndex();


            System.out.println("select quiz");}

        else{
           affiche.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/questions1.fxml"));
            Parent root = loader.load();
            Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            st.setScene(scene);
            st.show();
            retourner();
            System.out.println(retourner());
            questions irc=loader.getController();
            irc.setIdd(retourner());

    }}

    @FXML
    void affich(ActionEvent event) throws SQLException {

            affichage();


    }
    public boolean selectyyy() throws SQLException {
        if (!affiche.getSelectionModel().getSelectedItems().isEmpty()) {
            return true;
        }
        return false;
    }
    @FXML
    void modifier(ActionEvent event) throws SQLException {
        quiz quii  = affiche.getSelectionModel().getSelectedItem();
        //select();
        if (selectyyy()) {
                String t = nom.getText();
        quiz qu = new quiz(t);
        System.out.println(quii.getIdquiz());
        qs.update(qu, quii.getIdquiz());
        affichage();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("failed");
            alert.setContentText("selectionner une case ");
            alert.showAndWait();
        }



    }
   public int retourner(){


            // System.out.println(q.getIdquiz());
            return affiche.getItems().get(affiche.getSelectionModel().getSelectedIndex()).getIdquiz();
        }
      // quiz q = affiche.getItems().get(affiche.getSelectionModel().getSelectedIndex());
       // System.out.println(q.getIdquiz());


    @FXML
    void rec(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }
    public int retourn(int id){
        return id;
    }

    @FXML
    void supp(ActionEvent event) throws SQLException {
        if (selectyyy()) {
       quiz qu = affiche.getSelectionModel().getSelectedItem();
        System.out.println(qu.getIdquiz());
        qs.delete(qu.getIdquiz());
        affichage();}
         else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("failed");
            alert.setContentText("selectionner une ligne ");
            alert.showAndWait();
        }
    }

    public void quiz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void ajouter(ActionEvent event) {
    }


    public void formations(ActionEvent event) {
    }

    public void cours(ActionEvent event) {
    }



    public void salle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }


    public void reclama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void disc(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
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

    public void verspageadus(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }




























}