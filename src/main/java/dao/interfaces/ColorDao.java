package dao.interfaces;

import entity.car.Color;

import java.sql.SQLException;
import java.util.Optional;

public interface ColorDao {
    Optional<Integer> addColor(String title) throws SQLException;
    Optional<Color> getByTitle(String title) throws SQLException;
    Color getOrElseAddAndGet(String title) throws  SQLException;
    Optional<Color> getById(int id) throws SQLException;
    boolean remove(int id) throws SQLException;
}
