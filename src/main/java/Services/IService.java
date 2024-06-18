package Services;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IService <T> {

    void add1(T t) throws SQLException;



    //void add1(T t) throws SQLException;

  //  void add1(quiz q, formation f, user u) throws SQLException;

    //void add1(Reclamation reclamation) throws SQLException;

    void update(T t, int id) throws SQLException;


    void delete(int id) throws SQLException;

    public ObservableList<T> getAll() throws SQLException;

    ObservableList<T> get() throws SQLException;

    ObservableList getById(int id) throws  SQLException;

}
