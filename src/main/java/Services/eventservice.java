
package Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.event;
import utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class eventservice implements services.crud<event> {
    private Connection connection;

    public eventservice() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public List<event> afficher() throws SQLException {
        String sql = "select * from event";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<event> events = new ArrayList<>();
        while (rs.next()) {
            event e = new event();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setLieu(rs.getString("lieu"));
            e.setDate(rs.getDate("date").toLocalDate());
            e.setHeure(rs.getTime("heure").toLocalTime());
            e.setIdClub(rs.getInt("idclub"));
            e.setIdUser(rs.getInt("iduser"));
            events.add(e);
        }
        return events;
    }




    @Override
    public void add(event event) throws SQLException {
        String sql = "insert into event (name, lieu, date, heure, idclub, iduser) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getLieu());
        preparedStatement.setDate(3, Date.valueOf(event.getDate()));
        preparedStatement.setTime(4, Time.valueOf(event.getHeure()));
        preparedStatement.setInt(5, event.getIdClub());
        preparedStatement.setInt(6, event.getIdUser());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(event event) throws SQLException {
        String sql = "update event set lieu = ?, name = ?, date = ?, heure = ?, idclub = ?, iduser = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, event.getLieu());
        preparedStatement.setString(2, event.getName());
        preparedStatement.setDate(3, Date.valueOf(event.getDate()));
        preparedStatement.setTime(4, Time.valueOf(event.getHeure()));
        preparedStatement.setInt(5, event.getIdClub());
        preparedStatement.setInt(6, event.getIdUser());
        preparedStatement.setInt(7, event.getId());
        preparedStatement.executeUpdate();
    }
    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void delete(event eventToDelete) throws SQLException {
        try {
            // Vérifiez d'abord si eventToDelete n'est pas null
            if (eventToDelete != null) {
                String sql = "DELETE FROM event WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, eventToDelete.getId());
                preparedStatement.executeUpdate();
            } else {
                System.out.println("Error: eventToDelete is null");
            }
        } catch (SQLException e) {
            // Imprimez les détails de l'exception pour le débogage
            e.printStackTrace();
            throw e; // Rejetez l'exception après avoir imprimé les détails
        }
    }


    @Override
    public ObservableList<event> getAll() throws SQLException {
        ObservableList<event> events = FXCollections.observableArrayList();
        String sql = "SELECT * FROM event WHERE idclub = ? AND iduser = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 1); // Remplacez 1 par l'ID du club
        statement.setInt(2, 1); // Remplacez 1 par l'ID de l'utilisateur
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            event e = new event();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setLieu(rs.getString("lieu"));
            e.setDate(rs.getDate("date").toLocalDate());
            e.setHeure(rs.getTime("heure").toLocalTime());
            events.add(e); // Ajouter l'événement à la liste des événements
        }
        return events;
    }


    @Override
    public event getById(int id) throws SQLException {
        String sql = "SELECT name, lieu, date, heure, idclub, iduser FROM event WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String lieu = resultSet.getString("lieu");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime heure = resultSet.getTime("heure").toLocalTime();
            int idClub = resultSet.getInt("idclub");
            int idUser = resultSet.getInt("iduser");

            event e = new event(name, lieu, date, heure, idClub, idUser);
            e.setId(id);
            return e;
        } else {
            return null;
        }
    }

    @Override
    public void modifier(event eventToModify) throws SQLException {
        try {
            if (eventToModify != null) {
                String sql = "UPDATE event SET lieu = ?, name = ?, date = ?, heure = ?, idclub = ?, iduser = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, eventToModify.getLieu());
                preparedStatement.setString(2, eventToModify.getName());
                preparedStatement.setDate(3, Date.valueOf(eventToModify.getDate()));
                preparedStatement.setTime(4, Time.valueOf(eventToModify.getHeure()));
                preparedStatement.setInt(5, eventToModify.getIdClub());
                preparedStatement.setInt(6, eventToModify.getIdUser());
                preparedStatement.setInt(7, eventToModify.getId());
                preparedStatement.executeUpdate();
            } else {
                System.out.println("Error: eventToModify is null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}