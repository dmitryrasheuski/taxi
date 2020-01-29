package dao.impl.orm.jpa;

import entity.car.Car;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

public class CarDao implements dao.interfaces.CarDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public CarDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }

    @Override
    public Optional<Long> addCar(Car car) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> deleteCar(long idCar) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Car> getCarById(long idCar) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Car> getCarByDriver(long idDriver) throws SQLException {
        return Optional.empty();
    }
}
