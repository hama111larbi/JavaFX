package Services;

import entities.equipement;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipement implements IEquipement<equipement> {

    private Connection connection;

    public ServiceEquipement() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(equipement equip) throws SQLException {
        String req = "INSERT INTO equipements (salle_id, nom, brand, category, qr_code) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, equip.getSalle_id());
        pre.setString(2, equip.getNom());
        pre.setString(3, equip.getBrand());
        pre.setString(4, equip.getCategory());
        pre.setString(5, equip.getQr_code());
        pre.executeUpdate();
    }

    @Override
    public void modifier(equipement equip) throws SQLException {
        String req = "UPDATE equipements SET salle_id=?, nom=?, brand=?, category=?, qr_code=? WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, equip.getSalle_id());
        pre.setString(2, equip.getNom());
        pre.setString(3, equip.getBrand());
        pre.setString(4, equip.getCategory());
        pre.setString(5, equip.getQr_code());
        pre.setInt(6, equip.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(equipement equip) throws SQLException {
        String req = "DELETE FROM equipements WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, equip.getId());
        pre.executeUpdate();
    }

    @Override
    public List<equipement> afficher() throws SQLException {
        String req = "SELECT * FROM equipements";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<equipement> list = new ArrayList<>();
        while (res.next()) {
            equipement e = new equipement();
            e.setId(res.getInt("id"));
            e.setSalle_id(res.getInt("salle_id"));
            e.setNom(res.getString("nom"));
            e.setBrand(res.getString("brand"));
            e.setCategory(res.getString("category"));
            e.setQr_code(res.getString("qr_code"));
            list.add(e);
        }
        return list;
    }
}