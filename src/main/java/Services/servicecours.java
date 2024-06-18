package Services;

import models.cours;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicecours implements servicef<cours> {
    private Connection connection ;
    public servicecours(){
        connection = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(cours cours) throws SQLException {
        // Préparer la requête SQL
        String sql = "INSERT INTO cours (Nom_Cours, Description_Cours, IDfor) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Définir les valeurs des paramètres
        statement.setString(1, cours.getNom_Cours());
        statement.setBytes(2, cours.getDescription_Cours());
        statement.setInt(3, cours.getIDfor());

        // Exécuter la requête
        statement.executeUpdate();

    }


    @Override
    public void modifier(cours cours) throws SQLException {
        String sql = "UPDATE cours SET Nom_Cours = ? WHERE idCour = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cours.getNom_Cours());
        statement.setInt(2, cours.getIdCour());
        statement.executeUpdate();
    }
    public void deleteCourById(int idCour) throws SQLException {
        String sql = "DELETE FROM cours WHERE idCour = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCour);
        statement.executeUpdate();
    }
    @Override
    public void supprimer(cours cours) throws SQLException {
        // Préparer la requête SQL
        String sql = "DELETE FROM cours WHERE idCour = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Définir les valeurs des paramètres
        statement.setInt(1, cours.getIdCour());

        // Exécuter la requête
        statement.executeUpdate();
    }
    public boolean checkCourseExists(String Nom_Cours, int IDfor) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM cours WHERE Nom_Cours = ? AND IDfor  = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Nom_Cours);
            statement.setInt(2, IDfor);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // If count is greater than 0, course exists
            }
        }
        return false; // If no course with the given name and formation ID is found
    }

    @Override
    public List<cours> afficher() throws SQLException {

        String sql = "SELECT * FROM cours";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<cours> coursList = new ArrayList<>();

        while (resultSet.next()) {
            cours cours = new cours();
            cours.setIdCour(resultSet.getInt("idCour"));
            cours.setNom_Cours(resultSet.getString("Nom_Cours"));
            cours.setDescription_Cours(resultSet.getBytes("Description_Cours"));
            cours.setIDfor(resultSet.getInt("IDfor"));
            coursList.add(cours);
        }

        return coursList;
    }
}