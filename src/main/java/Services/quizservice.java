package Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.quiz;
import utils.MyDB;


import java.sql.*;

public class quizservice implements IService<quiz> {

    public quizservice(){
        connection = MyDB.getInstance().getConnection();
    }

    private Connection connection;





    @Override
    public void add1(quiz q) throws SQLException {
        String sql = "INSERT INTO quiz (nom,iduser) VALUES (?,8)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1,q.getNom());
        ps.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("quiz ajouté");
        alert.showAndWait();
    }





    @Override
    public void update(quiz q, int id) throws SQLException {
        String sql = "UPDATE quiz SET nom=? ,iduser=? WHERE idquiz=?";
       // user u= new user();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, q.getNom());
        preparedStatement.setInt(2,1);
        preparedStatement.setInt(3,id);
        preparedStatement.executeUpdate();
        System.out.println("quiz modifiée ");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("quiz modifiée");
        alert.showAndWait();
    }



    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from quiz where idquiz =?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();

    }
    @Override
    public ObservableList<quiz> getAll() throws SQLException {
        ObservableList<quiz>list = FXCollections.observableArrayList();
        String sql ="SELECT* FROM quiz WHERE iduser=4 ";
        Statement stat = connection.prepareStatement(sql);
        ResultSet rs= stat.executeQuery(sql);
        while (rs.next())
        {
          quiz q=new quiz();
            q.setId(rs.getInt("idquiz"));
           /// u.setId(rs.getInt("iduser"));
            q.setNom(rs.getString("nom"));
            list.add(q);
        }
        return list;
    }

    @Override
    public ObservableList<quiz> get() throws SQLException {
        return null;
    }

    @Override
    public ObservableList getById(int id) throws SQLException {

        return null;
    }
}
