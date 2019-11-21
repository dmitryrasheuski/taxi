package dao.interfaces;

import entity.car.Car;
import appException.dao.AppSqlException;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {

    long addCar(Car car) throws SQLException, AppSqlException;
    void deleteCar(long idCar) throws SQLException, AppSqlException;
    void updateStatus(long idCar, boolean status) throws SQLException, AppSqlException;
    Car getCarById(long idCar) throws SQLException, AppSqlException;
    Car getCarByDriver(long idDriver) throws SQLException, AppSqlException;
    List<Car> getListCarsByStatus(boolean status) throws SQLException, AppSqlException;
}
