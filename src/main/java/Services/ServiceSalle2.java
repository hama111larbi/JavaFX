package Services;

import entities.salleee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSalle2 implements ISalle<salleee> {

    private Connection connection;

    public ServiceSalle2() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(salleee salle) throws SQLException {
        String req = "INSERT INTO salle (matiere) VALUES (?)";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, salle.getMatiere());

            pre.executeUpdate();
            System.out.println("Salle added successfully!");
        }
    }

    @Override
    public void modifier(salleee salle, int id) throws SQLException {
        String req = "UPDATE salle SET matiere=? WHERE id=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, salle.getMatiere());
            pre.setInt(2, id);
            pre.executeUpdate();
            System.out.println("Salle updated successfully!");
        }
    }

    @Override
    public void supprimer(salleee salle, int id) throws SQLException {
        if (salle != null) {
            String req = "DELETE FROM salle WHERE id=?";
            try (PreparedStatement pre = connection.prepareStatement(req)) {
                pre.setInt(1, salle.getId());
                pre.executeUpdate();
            }
        } else {
            System.out.println("Error: salle is null");
        }
    }

    @Override
    public ObservableList<salleee> afficher() throws SQLException {
        List<salleee> salles = new ArrayList<>();
        String query = "SELECT * FROM salle";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String matiere = rs.getString("matiere");
                salles.add(new salleee(id, matiere));
            }
        }
        return FXCollections.observableArrayList(salles);
    }
}
