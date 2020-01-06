package dao.interfaces;

import entity.car.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CarDao {

    Optional<Long> addCar(Car car) throws SQLException;
    Optional<Integer> deleteCar(long idCar) throws SQLException;
    Optional<Car> getCarById(long idCar) throws SQLException;
    Optional<Car> getCarByDriver(long idDriver) throws SQLException;
}
