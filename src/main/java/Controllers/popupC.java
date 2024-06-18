package Controllers;

import Services.reponseservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Reponses;

import java.io.IOException;
import java.sql.SQLException;

public class popupC extends Popup {
    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    public Popup getPopup() {
        return popup;
    }

    Popup popup=new Popup();
    @FXML
    private VBox item;

    @FXML
    private TextArea description;


    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getIid() {
        return iid;
    }

    int iid;
reponseservice rs=new reponseservice();
    public void repondre(ActionEvent actionEvent) throws SQLException, IOException {

        getIid();
        System.out.println(getIid());
        //affichage();
        String qu = this.description.getText();
       /// if (!qu.trim().isEmpty()) {

            int j = getIid();
            System.out.println(getIid());
            Reponses qq = new Reponses(j, qu);

            rs.add1(qq);

            popup.hide();

           /// popup.getOnCloseRequest();

            // rs.getAll();
            //affiche.getItems();
          //  affichage();
            System.out.println("ajout avec success");

      ///  } else {
            System.out.println("ilyaa un champs vide");

        }
    @FXML
    void exit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin2.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }
       // affichage();
    }

