package Controllers;

import Services.reponseservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Reponses;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;


public class itemrecControlleradmin {

    @FXML
    private HBox itemC;
    @FXML
    private TextArea desc;
    @FXML
    private Label titre;

    //@FXML
    //private Label description;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
reponseservice rs=new reponseservice();
    private int id;
    @FXML
    private Label contenu;




        public void supprimer(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
            System.out.println(getId());
            rs.delete(getId());
                //itemC.get
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin2.fxml"));
            Parent root = loader.load();
            Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            st.setScene(scene);
            st.show();


        }

    public void modifier(ActionEvent actionEvent) throws SQLException, IOException {
     Reponses q = rs.getBycontenu(getId());

         q.setIdrec(getId()); // Remove this line if it's redundant
       System.out.println(q.getContenu());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/popupp.fxml"));
        VBox popupContent = loader.load();
        Popup pop = new Popup();
        pop.getContent().add(popupContent);
        popupppC controller = loader.getController();
         controller.setContenuu(q.getContenu());
         controller.setId(getId());
         controller.setPopup(pop);
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        pop.show(st);


    }
}