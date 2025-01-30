package org.JavaFxProject.Hotel.Services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T var1) throws SQLException;

    void supprimer(T var1) throws SQLException;

    void update(T var1) throws SQLException;

    T getById(int var1) throws SQLException;

    List<T> getAll() throws SQLException;
}
