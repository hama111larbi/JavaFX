package Services;

import models.categorie;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicecategorie implements servicef<categorie> {
    private Connection connection;

    public servicecategorie() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(categorie categorie) throws SQLException {

        String req = "INSERT INTO categorie(nomCategorie ) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, categorie.getNomCategorie());
        preparedStatement.executeUpdate();

    }

    @Override
    public void modifier(categorie categorie) throws SQLException {
        String req = "UPDATE  categorie set   nomCategorie=? WHERE idCategorie=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, categorie.getNomCategorie());
        pre.setInt(2, categorie.getIdCategorie());


        pre.executeUpdate();

    }

    @Override
    public void supprimer(categorie categorie) throws SQLException {
        String req = "delete from categorie where idCategorie=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, categorie.getIdCategorie());
        pre.executeUpdate();

    }
    public boolean checkCategoryExists(String categoryName) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM categorie WHERE nomCategorie = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // If count is greater than 0, category exists
            }
        }
        return false; // If no category with the given name is found
    }
    @Override
    public List<categorie> afficher() throws SQLException {
        String req = "SELECT * FROM categorie";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<categorie> list = new ArrayList<>(); // Corrected to store Categorie objects

        int rowCount = 0; // Variable to track the number of rows processed

        while (res.next()) {
            categorie c = new categorie();
            c.setIdCategorie(res.getInt(1));
            c.setNomCategorie(res.getString("nomCategorie"));

            list.add(c);
        }
            return list;
        }
    }

