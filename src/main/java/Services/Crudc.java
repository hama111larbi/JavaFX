package services;

import models.Club;

import java.sql.SQLException;
import java.util.List;

public interface Crudc<C> {
    List<Club> afficher() throws SQLException;

    void add(Club club) throws SQLException;

    void update(Club club) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(Club clubToDelete) throws SQLException;

    List<Club> getAll() throws SQLException;

    Club getById(int id) throws SQLException;

    void modifier(Club clubToModify) throws SQLException;
}
