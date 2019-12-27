package dao.interfaces;

import entity.car.Color;

import java.sql.SQLException;
import java.util.Optional;

public interface ColorDao {
    Optional<Integer> addColor(String title) throws SQLException;
    Optional<Color> getColorByTitle(String title) throws SQLException;
    Optional<Integer> getIdOrElseAddAndGet(String title) throws  SQLException;
}
