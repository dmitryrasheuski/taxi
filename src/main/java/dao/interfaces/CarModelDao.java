package dao.interfaces;

import entity.car.CarModel;

import java.sql.SQLException;
import java.util.Optional;

public interface CarModelDao {
    Optional<Integer> addCarModel(CarModel model) throws SQLException;
    Optional<CarModel> getCarModel(String title) throws SQLException;
    Optional<CarModel> getOrElseAddAndGetId(String title) throws SQLException;
}
