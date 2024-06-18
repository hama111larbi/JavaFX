package Services;

import models.formation;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class serviceFormation implements servicef<formation> {

    private static Connection connection;

    public serviceFormation() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(formation formation) throws SQLException {
        String req = "insert into formation (nomCategorie,Nom_de_Formation,Description,Durée,Niveau,Date_Deb,Date_Fin,Coût) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, formation.getNomCategorie());
        preparedStatement.setString(2, formation.getNom_de_Formation());
        preparedStatement.setString(3, formation.getDescription());
        preparedStatement.setString(4, formation.getDurée());
        preparedStatement.setString(5, formation.getNiveau());
        preparedStatement.setDate(6, new Date(formation.getDate_Deb().getTime()));
        preparedStatement.setDate(7, new Date(formation.getDate_Fin().getTime()));
        preparedStatement.setString(8, formation.getCoût());

        preparedStatement.executeUpdate();


    }

    @Override
    public void modifier(formation formation) throws SQLException {

        String req = "UPDATE formation SET Nom_de_Formation=?, Description=?, Durée=?, Niveau=?, Date_Deb=?, Date_Fin=?, Coût=? WHERE ID_de_Formation =?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, formation.getNom_de_Formation());
            pre.setString(2, formation.getDescription());
            pre.setString(3, formation.getDurée());
            pre.setString(4, formation.getNiveau());
            pre.setDate(5, new Date(formation.getDate_Deb().getTime()));
            pre.setDate(6, new Date(formation.getDate_Fin().getTime()));
            pre.setString(7, formation.getCoût());
            pre.setInt(8, formation.getID_de_Formation());
            pre.executeUpdate();
        }
    }

    @Override
    public void supprimer(formation formation) throws SQLException {
        String req = "DELETE FROM formation WHERE ID_de_Formation=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, formation.getID_de_Formation());
        pre.executeUpdate();
    }

    public static List<Integer> getAllFormationIds() throws SQLException {
        List<Integer> formationIds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Établir la connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi_avengers-1", "votre_utilisateur", "votre_mot_de_passe");

            // Préparer la requête SQL
            stmt = conn.prepareStatement("SELECT idFormation FROM Formation");

            // Exécuter la requête
            rs = stmt.executeQuery();

            // Parcourir les résultats de la requête
            while (rs.next()) {
                int idFormation = rs.getInt("idFormation");
                formationIds.add(idFormation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources JDBC
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return formationIds;
    }

    public static formation rechercherParnom(String nomf) throws SQLException {
        String req = "SELECT * FROM formation WHERE Nom_de_Formation = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, nomf);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // If a result is found, construct a Formation object and return it
            formation formation = new formation();
            formation.setID_de_Formation(resultSet.getInt("ID_de_Formation"));
            formation.setNomCategorie(resultSet.getString("NomCategorie"));
            formation.setNom_de_Formation(resultSet.getString("Nom_de_Formation"));
            formation.setDescription(resultSet.getString("Description"));
            formation.setDurée(resultSet.getString("Durée"));
            formation.setNiveau(resultSet.getString("Niveau"));
            formation.setDate_Deb(resultSet.getDate("Date_Deb"));
            formation.setDate_Fin(resultSet.getDate("Date_Fin"));
            formation.setCoût(resultSet.getString("Coût"));

            return formation;
        } else {
            // If no result is found, return null or throw an exception
            return null;
            // Or throw new IllegalArgumentException("Formation with titre " + titre + " not found");
        }
    }
    @Override
    public List<formation> afficher() throws SQLException {

        String req = "SELECT * FROM formation";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<formation> list = new ArrayList<>();

        int rowCount = 0; // Variable to track the number of rows processed

        while (res.next()) {
            formation f = new formation();
            f.setID_de_Formation(res.getInt(1));
            f.setNomCategorie(res.getString("nomCategorie"));
            f.setNom_de_Formation(res.getString("Nom_de_Formation"));
            f.setDescription(res.getString("Description"));
            f.setDurée(res.getString("Durée"));
            f.setNiveau(res.getString("Niveau"));
            f.setDate_Deb(res.getDate("Date_Deb"));
            f.setDate_Fin(res.getDate("Date_Fin"));
            f.setCoût(res.getString("Coût")); // Set nomCategorie

            list.add(f);
        }


        return list;
    }
}
