package Controllers;

import Services.BadWordFilterService;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.*;
    import javafx.scene.control.*;
    import Services.reclamationservice;
        import Services.IService;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.stage.Stage;
    import models.Reclamation;
    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;

    import javax.imageio.IIOParam;
    import java.awt.event.MouseEvent;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.URL;
    import java.net.URLConnection;
    import java.net.URLEncoder;
    import java.sql.SQLException;
    import java.util.List;
    import java.util.Objects;
    import java.util.Random;
// Install the Java helper library from twilio.com/docs/java/install
    import com.twilio.Twilio;
    import com.twilio.rest.api.v2010.account.Message;
    import com.twilio.type.PhoneNumber;

public class ajouterreclamation {
    String[] badWordsArray = {"5ra", "le", "non","fuck","pute","yezi","edara","esprit","ee"};

    BadWordFilterService filter=new BadWordFilterService(badWordsArray);


    @FXML
    private TextField titre;


    @FXML
    private TableView<Reclamation> affiche;

    // @FXML
    // private TableColumn<Reclamation, Integer> idt;

    @FXML
    private TableColumn<Reclamation, String> titret;

    @FXML
    private TableColumn<Reclamation, String> descriptiont;
    @FXML
    private TextArea description;
    reclamationservice rs = new reclamationservice();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String otpStr;

    public ajouterreclamation() throws IOException {
    }

    private static final String API_KEY = "e53e84a8aamsh105373ba7c877dfp19f371jsn84cc5c9c413a\n";

    public void affichage() throws SQLException {
        ObservableList<Reclamation> l = rs.getAll();
        // affiche.getItems().clear();
        affiche.setItems(l);
        //idt.setCellValueFactory(new PropertyValueFactory<Reclamation,Integer>("id"));
        titret.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("titre"));
        descriptiont.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
        //rs.update();


    }

    @FXML
    void initialize() throws SQLException {affichage();}
    @FXML
    void ajouter(ActionEvent event) throws SQLException {


        if (filter.hasBadWord(titre.getText()) || filter.hasBadWord(description.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ALERT !!");
            alert.setContentText("BAD WORD");
            alert.showAndWait();

        }else {
        try {

            rs.add1((new Reclamation(titre.getText(), description.getText())));

            affichage();
            // rs.getAll(new Reclamation(titre.getText(), description.getText()));
            // rs.getAll(new Reclamation());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}


        //""""" idt.setCellFactory(new PropertyValueFactory<Reclamation,Integer>("id"));


    }



    @FXML
    void update(ActionEvent event) throws SQLException {


        Reclamation r = affiche.getSelectionModel().getSelectedItem();

        String t=titre.getText();
        String d=description.getText();
        Reclamation re=new Reclamation(t,d);
        System.out.println(r.getId());
        rs.update(re,r.getId());
        affichage();
    }
    @FXML
    void quiz(ActionEvent event)  throws IOException {




        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));

        Parent root = loader.load();
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
       scene =new Scene(root);
       stage.setScene(scene);
       stage.show();


        ///  if(event.isConsumed())



    }

    public void select() throws SQLException {


        Reclamation r=affiche.getSelectionModel().getSelectedItem();
        int num=affiche.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){return;}
        titre.setText(String.valueOf(r.getTitre()));
        description.setText(String.valueOf(r.getDescription()));

    }




    public void emplacement(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();


    }

    @FXML
  public   void sms(ActionEvent event) {
       // Reclamation q=ne

            // Find your Account Sid and Token at twilio.com/console
             final String ACCOUNT_SID = "ACab83a00aa24c2945460363efc3c77fed";
             final String AUTH_TOKEN = "ef4709f9deb9baa5f41868c223744617";


                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
                                new com.twilio.type.PhoneNumber("+21621782711"),
                                new com.twilio.type.PhoneNumber("+14243560944"),
                                description.getText())
                        .create();

                System.out.println(message.getSid());
            }



    public void supp(ActionEvent actionEvent) throws SQLException {
        Reclamation r = affiche.getSelectionModel().getSelectedItem() ;
        System.out.println(r.getId());
        rs.delete(r.getId());
        affichage();

    }


    public void forma(ActionEvent event) {
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

    public void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
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
}

