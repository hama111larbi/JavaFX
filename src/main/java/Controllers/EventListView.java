package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.twilio.rest.verify.v2.service.AccessToken;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.List;

import javafx.stage.Stage;
import models.event;


import Services.eventservice;
public class EventListView {
    @FXML
    private ResourceBundle resources;




    @FXML
    private Button afficher;

    @FXML
    private ListView<event> EventListView;




    private eventservice services;
    @FXML
    private event selectedevent; // Store the selected event

    public EventListView() {
    }

    @FXML
    void eventetudiant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface etudiant.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }
    @FXML
    void eventt(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventMangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }
    eventservice es = new eventservice();


    // Inject EventService into the controller
    eventservice service = new eventservice();




    private event findeventbyId(int id) {
        try {
            List<event> events = es.afficher();
            for (event event : events) {
                if (event.getId() == id) {
                    return event;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }












    @FXML
    void afficher(ActionEvent event) {
        try {
            ObservableList<event> events = es.getAll();

            // Clear any existing items in the ListView
            ///  eventlistview.getItems().clear();

            // Add each event object to the ListView with a custom string representation
            //for (event eventt : events) {
            // Personnalisez la représentation de chaîne pour l'événement
            // Ajouter la représentation de chaîne à la ListView
            EventListView.setItems(events);
            // }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des événements: " + e.getMessage());
        }
    }


    public void profil(ActionEvent actionEvent) {
    }

    public void reclama(ActionEvent actionEvent) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
            Parent root = loader.load();
            Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            st.setScene(scene);
            st.show();

    }
    public void emplacement(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();


    }
    public void salles(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }

    public void formation(ActionEvent actionEvent) {
    }

    public void quiz(ActionEvent actionEvent) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
            Parent root = loader.load();
            Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            st.setScene(scene);
            st.show();
        }


    public void dispo(ActionEvent actionEvent) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

}
