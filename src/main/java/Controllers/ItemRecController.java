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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Questions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ItemRecController {

    public Map<Integer, Boolean> answerSelectedMap  = new HashMap<>(); // Map pour stocker si une réponse a été sélectionnée pour chaque question
public int score=0;
quiz qz=new quiz();

    public quiz getQz() {
        return qz;
    }

    public void setQz(quiz qz) {
        this.qz = qz;
    }

    public void setId(Label id) {
        this.id = id;
    }

    public Label getId() {
        return id;
    }
    @FXML
    private Button Start;
    @FXML
    private Label id;

    @FXML
    private HBox itemC;

    @FXML
    private Label nom;

    @FXML
    private Label nbrqst;


    public void setI(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    private int i;
questionservice rs=new questionservice();
    public void Start(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
        Parent root = loader.load();
        quiz q=loader.getController();
        setQz(q);
        nbrqst.getScene().setRoot(root);
        qz.getPnitems().getChildren().clear();

        ObservableList<Questions> R = null;
        try {
            int k=Integer.parseInt(id.getText());
            System.out.println(k);
            R = rs.get(k);

            for (int i = 0; i < R.size(); i++) {

                final int j = i;
                Questions qu = R.get(j);
                // q.setIdquiz(n);
                // System.out.println(getId());
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/itemquest.fxml"));
                Node node = loader1.load();


                ((Label) node.lookup("#quest")).setText(qu.getQuestion());
                ((Button) node.lookup("#op1")).setText(qu.getOp1());
                ((Button) node.lookup("#op2")).setText(qu.getOp2());
                ((Button) node.lookup("#op3")).setText(qu.getOp3());
                qz.getPnitems().getChildren().add(node);

                Button button1 = (Button) node.lookup("#op1");
                Button button2 = (Button) node.lookup("#op2");
                Button button3 = (Button) node.lookup("#op3");
                boolean answerSelected = answerSelectedMap.getOrDefault(qu.getIdquest(), false);
                if (answerSelected) {
                    button1.setDisable(true);
                    button2.setDisable(true);
                    button3.setDisable(true);
                } else {
                    button1.setOnAction(event -> {
                        try {
                            handleButtonPress(button1, qu.getOp1(), qu.getAnswer(), button2, button3);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    button2.setOnAction(event -> {
                        try {
                            handleButtonPress(button2, qu.getOp2(), qu.getAnswer(), button1, button3);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    button3.setOnAction(event -> {
                        try {
                            handleButtonPress(button3, qu.getOp3(), qu.getAnswer(), button1, button2);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

            }


            if (q.quizCompleted) {
                Start.setDisable(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // System.out.println(getI());
       // System.out.println(irc.n);
    }
    @FXML
    void quiz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nvquiz.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();
quiz qz=loader.getController();
qz.sc =score;
    }

    @FXML
    void reclama(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterreclamation.fxml"));
        Parent root = loader.load();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

    }

    private void handleButtonPress(Button button, String selectedOption, String correctAnswer,Button... otherButtons) throws SQLException, IOException {
        ObservableList<Questions> R = null;

            int k=Integer.parseInt(id.getText());
            System.out.println(k);
            R = rs.get(k);


                Questions qu = R.get(i);
        button.setDisable(true);

        for (Button otherButton : otherButtons) {

            otherButton.setDisable(true);
        }

        if (selectedOption.equals(correctAnswer)) {
            score=score+10;

            System.out.println("Bonne réponse : " + button.getText());
        } else {

            System.out.println("Mauvaise réponse : " + button.getText());
        }

        answerSelectedMap.put(qu.getIdquest(), true);
        System.out.println("Vous avez eu  "+score +"/" + (Integer.parseInt(nbrqst.getText())*10));
        System.out.println(score);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultat");
        alert.setContentText("Vous avez obtenu " +score+ "/" +(Integer.parseInt(nbrqst.getText())*10));
        alert.showAndWait();
       // score= Integer.parseInt(+score + "/" +(Integer.parseInt(nbrqst.getText())*10));
        boolean quiz=true ;
      if(quiz==true)
      {
          Start.setDisable(true);
      }

    }
}
