package dao.interfaces;

import entity.car.CarModel;

import java.sql.SQLException;
import java.util.Optional;

public interface CarModelDao {
    Optional<Long> addCarModel(CarModel model) throws SQLException;
    Optional<CarModel> getCarModel(String title) throws SQLException;
    CarModel getOrElseAddAndGet(String title) throws SQLException;
    Optional<CarModel> getById(long id) throws SQLException;
    boolean removeCarModel(CarModel model) throws SQLException;
}
