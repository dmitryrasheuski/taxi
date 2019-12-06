package dao.interfaces;

import entity.car.Car;
import appException.dao.AppSqlException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CarDao {

    Optional<Long> addCar(Car car) throws SQLException, AppSqlException;
    Optional<Integer> deleteCar(long idCar) throws SQLException, AppSqlException;
    Optional<Integer> updateStatus(long idCar, boolean status) throws SQLException, AppSqlException;
    Optional<Car> getCarById(long idCar) throws SQLException, AppSqlException;
    Optional<Car> getCarByDriver(long idDriver) throws SQLException, AppSqlException;
    Optional<List<Car>> getListCarsByStatus(boolean status) throws SQLException, AppSqlException;
}
