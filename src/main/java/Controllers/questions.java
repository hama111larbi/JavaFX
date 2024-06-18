package Controllers;

import Services.questionservice;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Questions;

import java.io.IOException;
import java.sql.SQLException;
//package Controllers;
import javafx.scene.control.TextField;
import models.quiz;

public class questions {

    @FXML
    private TextField ecrire1;

    @FXML
    private TextField ecrire2;

    @FXML
    private TextField ecrire3;

    @FXML
    private TextField question;

    public int getIdd() {
        return idd;
    }

    @FXML
    private ComboBox<String> answerbox;

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public void setNbrquestt(int nbrquestt) {
        this.nbrquestt = nbrquestt;
    }

    public int getNbrquestt() {
        return nbrquestt;
    }

    private int nbrquestt;

    public int idd;
    quizcontroller qz = new quizcontroller();

    //@FXML
    //private TextField ecrire11;
    //  private ArrayList<Questions>questions=new ArrayList<>();
    questionservice qs = new questionservice();
    //public class questions {

    @FXML
    void initialize() throws NoSuchFieldException {
        answerbox.setVisible(false);

        /// System.out.println(getAff());

        ///question.setText(String.valueOf(quizcontroller.class.getField(question.getId())));
    }

    @FXML
    void quiz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    @FXML
    void rec(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }


    public void submit(ActionEvent actionEvent) throws SQLException, IOException {
        //System.out.println(getIdd());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/questions25.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
    }

    @FXML
    void affi(ActionEvent event) {
        answerbox.setVisible(true);
        String qu = this.question.getText();
        String op1 = this.ecrire1.getText();
        String op2 = this.ecrire2.getText();
        String op3 = this.ecrire3.getText();
        answerbox.getItems().addAll(op1, op2, op3);
        //answerbox.setItems(op1,op2,op3);
        answerbox.getSelectionModel().getSelectedItem();
        answerbox.getValue();

    }

    public void ajout(ActionEvent actionEvent) throws SQLException, IOException {
        quiz qui = new quiz();
        affi(actionEvent);
        getIdd();
        answerbox.setVisible(true);
        //System.out.println(qui.getNbrquest());
        Questions q = new Questions();

                String qu = this.question.getText();
                String op1 = this.ecrire1.getText();
                String op2 = this.ecrire2.getText();
                String op3 = this.ecrire3.getText();
                if (!((op1 == op2) || (op1 == op3) || (op2 == op3))) {
                    //  System.out.println(Integer.parseInt(q.getQuestion()));
                    answerbox.getItems().addAll(op1, op2, op3);
                    //answerbox.setItems(op1,op2,op3);
                    answerbox.getSelectionModel().getSelectedItem();
                    answerbox.getValue();

                    if ((!qu.trim().isEmpty() && !op1.trim().isEmpty() && !op2.trim().isEmpty() && !op3.trim().isEmpty() && answerbox != null)) {
                        if ((qu.endsWith("?"))) {
                            String an = answerbox.getValue();
                            int j = getIdd();
                            Questions qq = new Questions(op1, op2, op3, an, qu, j);
                            qs.add1(qq);
                            System.out.println("ajout avec success");
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("failed");
                            alert.setContentText("ajout avec success ");
                            alert.showAndWait();
                            // questions.add(q);
                            question.clear();
                            ecrire1.clear();
                            ecrire2.clear();
                            ecrire3.clear();
                            answerbox.getItems().clear();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("failed");
                            alert.setContentText("ajoutez une ?");
                            alert.showAndWait();
                        }
                    } else {

                        System.out.println("remplir toutes les champs  ");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("failed");
                        alert.setContentText("remplir toutes les champs  ");
                        alert.showAndWait();
                    }
                } else {

                    System.out.println("options doivent etre differentes  ");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("failed");
                    alert.setContentText("options doivent etre differentes   ");
                    alert.showAndWait();
                }
            }









    @FXML
    void ajouter(ActionEvent event) {

    }



    @FXML
    void cours(ActionEvent event) {

    }



    @FXML
    void formations(ActionEvent event) {

    }



    @FXML
    void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationadmin.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }



    @FXML
    void salle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/salleManagement.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }


    public void event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eventmangement.fxml"));
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









