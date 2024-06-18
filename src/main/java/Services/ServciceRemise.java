package Services;


import entity.Remise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServciceRemise implements Services.IPaiement<Remise> {
    private Connection connection;

    public ServciceRemise() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Remise remise) throws SQLException {
        String req = "INSERT INTO remise (montant,pourcentage, montantaprespourcentage) VALUES (?, ?, ?)";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            //pre.setInt(1, paiement.getIDformation());
            pre.setFloat(1, remise.getMontant());
            pre.setInt(2, remise.getPourcentage());
            pre.setFloat(3, remise.getMontantaprespourcentage());
            pre.executeUpdate();
            System.out.println("Paiement added successfully!");
        }
    }

    @Override
    public void modifier(Remise remise, int id) throws SQLException {
        String req = "UPDATE remise SET montant=?, pourcentage=?, montantaprespourcentage=? WHERE id=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setFloat(1, remise.getMontant());
            pre.setInt(2, remise.getPourcentage());
            pre.setFloat(3, remise.getMontantaprespourcentage());
            pre.setInt(4, id);
            pre.executeUpdate();
            System.out.println("Paiement updated successfully!");
        }
    }


    @Override
    public void supprimer(Remise remise, int id) throws SQLException {
        String req = "DELETE FROM remise WHERE id=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, id);
            pre.executeUpdate();
            System.out.println("Paiement deleted successfully!");
        }
    }




    @Override
    public ObservableList<Remise> afficher() throws SQLException {
        String req = "SELECT * FROM remise";
        ObservableList<Remise> list = FXCollections.observableArrayList();
        PreparedStatement pre = connection.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        while (res.next()) {
            Remise r = new Remise();
            r.setId(res.getInt("id"));
            r.setMontant(res.getFloat("Montant"));
            r.setPourcentage(res.getInt("pourcentage"));
            r.setMontantaprespourcentage(res.getInt("montantaprespourcentage"));
            list.add(r);
        }

        return list;
    }
}