package Controllers;

import Services.reponseservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Reponses;

import java.io.IOException;
import java.sql.SQLException;

public class popupppC {

        @FXML
        private VBox item;

        public void setContenuu(String contenu) {
            this.contenuu = contenu;
        }

    public void setPopup(Popup popup) {
        this.pop = popup;
    }

    public Popup getPop() {
        return pop;
    }

    Popup pop=new popupC();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
reponseservice rs =new reponseservice();
    private int id;

        public String getContenu() {
            return contenuu;
        }

        private String contenuu;
        @FXML
        private TextArea contenu;

        @FXML
        void exit(ActionEvent event) {

        }

        @FXML
        void modifier(ActionEvent event) throws SQLException, IOException {
            Reponses q = rs.getBycontenu(getId());
            System.out.println(getContenu());
            q.setIdrep(getId()); // Remove this line if it's redundant
            System.out.println(q.getContenu());
       q.setContenu(contenu.getText());

        // q.setIdrep(getId());
        // select();
        ; // Assuming desc is a Label or TextArea
        String qu = contenu.getText();
        int j = getId();
        System.out.println(getId());
        // String nomquiz, int nbques, int idquiz, String question, String op1, String op2, String op3, String answer, int idquest
        Reponses quuu = new Reponses(j, qu);
        System.out.println(j);
            System.out.println(quuu);
            System.out.println(q.getIdrep());
            System.out.println(q.getIdrec());
        System.out.println(q.getIdrep());
        rs.update(quuu,q.getIdrep());
        pop.hide();
            Stage st1 = (Stage) pop.getOwnerWindow();

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/reclamationadmin2.fxml"));
            Parent root = loader1.load();
            Scene scene = new Scene(root);
            st1.setScene(scene);
            st1.show();
            // Fermer la fenêtre précédente


        }
}
