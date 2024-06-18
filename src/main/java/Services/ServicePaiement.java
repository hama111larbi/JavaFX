package Services;

import utils.MyDB;
import entity.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ServicePaiement  implements Services.IPaiement<Payment> {
    private Connection connection;

    public ServicePaiement() {
        connection = MyDB.getInstance().getConnection();
    }


    public void ajouter(Payment paiement) throws SQLException {
        String req = "INSERT INTO payment (montant, moyendepaiement,email,date) VALUES (?,?, ?, ?)";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            //pre.setInt(1, paiement.getIDformation());
            //pre.setInt(2, paiement.getIduser());
            pre.setFloat(1, paiement.getMontant());
            pre.setString(2, paiement.getMoyendepaiement());
            pre.setString(3, paiement.getEmail());
            pre.setTimestamp(4, Timestamp.valueOf(paiement.getDate()));
            pre.executeUpdate();
            System.out.println("Paiement added successfully!");
        }
    }

    @Override
    public void modifier(Payment payment, int id) throws SQLException {

    }

    /*@Override
    public void modifier(Payment paiement, int id) throws SQLException {
        String req = "UPDATE payment SET montant=?, moyendepaiement=?, email=?, date=? WHERE id=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, 1);
            pre.setInt(2,1);
            pre.setFloat(3, paiement.getMontant());
            pre.setString(4, paiement.getMoyendepaiement());
            pre.setInt(5, id);
            pre.executeUpdate();
            System.out.println("Paiement updated successfully!");
        }
    }*/

    @Override
    public void supprimer(Payment paiement, int id) throws SQLException {

    }

    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM payment WHERE id=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, id);
            pre.executeUpdate();
            System.out.println("Paiement deleted successfully!");
        }
    }

    public ObservableList<Payment> afficher() throws SQLException {
        String req = "SELECT * FROM payment";
        ObservableList<Payment> list = FXCollections.observableArrayList();
        PreparedStatement pre= connection.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        while (res.next()) {
            Payment paiement = new Payment();
            paiement.setId(res.getInt("id"));
            // res.getInt("IDformation"),
            // res.getInt("iduser"),
            paiement.setMontant( res.getFloat("montant"));
            paiement.setMoyendepaiement( res.getString("moyendepaiement"));

            list.add(paiement);


        }

        return list ;
    }
}
