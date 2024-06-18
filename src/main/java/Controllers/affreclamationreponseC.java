package Controllers;

import Services.reponseservice;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Questions;
import models.Reclamation;
import models.Reponses;
import models.quiz;

import java.io.IOException;
import java.sql.SQLException;

public class affreclamationreponseC  {
    @FXML

    public TextArea desc;

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getIid() {
        return iid;
    }

    private int iid;
   // private TextArea desc;
    @FXML
    private VBox pitems;
    int id;
    //@FXML
    // private TextArea description;
    @FXML
    private ListView<Reponses> affiche;

    reponseservice rs = new reponseservice();

    @FXML
    void initialize() throws SQLException {
       affichage();
    }




    public void quiz(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }




    public void affichage() throws SQLException {
        ObservableList<Reponses> R = rs.get();

            for (int i = 0; i < R.size(); i++) {
                try {
                    final int j = i;
                    Reponses q = R.get(j);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemrecadmin.fxml"));
                    Node node = loader.load();

                    ((Label) node.lookup("#titre")).setText(q.getTitre());
                    ((Label) node.lookup("#description")).setText(q.getDescription());
                    ((Label) node.lookup("#contenu")).setText(q.getContenu());

                    System.out.println();
                    itemrecControlleradmin irc = loader.getController();
                    irc.setId(q.getIdrep());
                  ///  System.out.println(q.getIdrep());
                    ///irc.setId_user_reponse(Reclamation.getId());
                    // Give the items some effect
                    node.setOnMouseEntered(event -> {
                        node.setStyle("-fx-background-color : #ffffff");
                    });
                    node.setOnMouseExited(event -> {
                        node.setStyle("-fx-background-color : #ffffff");
                    });

                    pitems.getChildren().add(node);



                    //affichage();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    public void eee(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }


    public void butt(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }
    //  public int getId() {
    ///return id;
    //  }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSuppid() {
        return suppid;
    }
    private int suppid;

    public void setSuppid(int suppid) {
        this.suppid = suppid;
    }


    public void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void formations(ActionEvent event) {
    }

    public void event(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventmangement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    public void cours(ActionEvent event) {
    }

    public void verspageadus(ActionEvent event) {
    }

    public void ajouter(ActionEvent event) {

    }
    public void salle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

}