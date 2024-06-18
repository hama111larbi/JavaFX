package Services;

import models.Club;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


    public class ClubService implements services.Crudc<Club> {
        private Connection connection;

        public ClubService() {
            connection = MyDB.getInstance().getConnection();
        }

        @Override
        public List<Club> afficher() throws SQLException {
            String req = "SELECT * FROM club";
            Statement ste = connection.createStatement();
            ResultSet res = ste.executeQuery(req);
            List<Club> list = new ArrayList<>();
            while (res.next()) {
                Club club = new Club(
                        res.getInt("idclub"),
                        res.getInt("iduser"),
                        res.getString("nameclub"),
                        res.getString("email"),
                        res.getInt("numtlf")
                );
                list.add(club);
            }
            return list;
        }

        @Override
        public void add(Club club) throws SQLException {
            String sql = "INSERT INTO club (iduser, nameclub, email, numtlf) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, club.getIduser());
            preparedStatement.setString(2, club.getNameclub());
            preparedStatement.setString(3, club.getEmail());
            preparedStatement.setInt(4, club.getNumtlf());
            preparedStatement.executeUpdate();
        }

        @Override
        public void update(Club club) throws SQLException {
            String sql = "UPDATE club SET iduser = ?, nameclub = ?, email = ?, numtlf = ? WHERE idclub = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, club.getIduser());
            preparedStatement.setString(2, club.getNameclub());
            preparedStatement.setString(3, club.getEmail());
            preparedStatement.setInt(4, club.getNumtlf());
            preparedStatement.setInt(5, club.getIdclub());
            preparedStatement.executeUpdate();
        }

        @Override
        public void delete(int id) throws SQLException {
            String sql = "DELETE FROM club WHERE idclub = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }

        @Override
        public void delete(Club clubToDelete) throws SQLException {
            if (clubToDelete != null) {
                delete(clubToDelete.getIdclub());
            } else {
                System.out.println("Error: clubToDelete is null");
            }
        }

        @Override
        public List<Club> getAll() throws SQLException {
            String sql = "SELECT * FROM club";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<Club> clubs = new ArrayList<>();
            while (rs.next()) {
                Club club = new Club(
                        rs.getInt("idclub"),
                        rs.getInt("iduser"),
                        rs.getString("nameclub"),
                        rs.getString("email"),
                        rs.getInt("numtlf")
                );
                clubs.add(club);
            }
            return clubs;
        }

        @Override
        public Club getById(int id) throws SQLException {
            String sql = "SELECT * FROM club WHERE idclub = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Club club = new Club(
                        resultSet.getInt("idclub"),
                        resultSet.getInt("iduser"),
                        resultSet.getString("nameclub"),
                        resultSet.getString("email"),
                        resultSet.getInt("numtlf")
                );
                return club;
            } else {
                return null;
            }
        }

        @Override
        public void modifier(Club clubToModify) throws SQLException {
            String sql = "UPDATE club SET iduser = ?, nameclub = ?, email = ?, numtlf = ? WHERE idclub = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clubToModify.getIduser());
            preparedStatement.setString(2, clubToModify.getNameclub());
            preparedStatement.setString(3, clubToModify.getEmail());
            preparedStatement.setInt(4, clubToModify.getNumtlf());
            preparedStatement.setInt(5, clubToModify.getIdclub());
            preparedStatement.executeUpdate();
        }
    }

