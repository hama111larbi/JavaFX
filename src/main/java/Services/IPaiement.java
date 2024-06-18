package Services;


import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IPaiement <T>{
    public void ajouter (T t) throws SQLException;
    public void modifier (T t,int id) throws SQLException;
    public void supprimer (T t, int id) throws SQLException;
    public ObservableList<T> afficher () throws SQLException;


}