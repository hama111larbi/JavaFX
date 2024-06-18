package services;

import models.event;

import java.sql.SQLException;
import java.util.List;

public interface crud<T> {

    List<event> afficher() throws SQLException;

    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(event eventToDelete) throws SQLException;

    List<T> getAll() throws SQLException;
    
    T getById(int id) throws  SQLException;


    void modifier(event eventToModify) throws SQLException;
}
