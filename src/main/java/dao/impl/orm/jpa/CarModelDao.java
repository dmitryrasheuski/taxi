package dao.impl.orm.jpa;

import entity.car.CarModel;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

public class CarModelDao implements dao.interfaces.CarModelDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public CarModelDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }

    @Override
    public Optional<Integer> addCarModel(CarModel model) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<CarModel> getCarModel(String title) throws SQLException {
        return Optional.empty();
    }

    @Override
    public CarModel getOrElseAddAndGet(String title) throws SQLException {
        return null;
    }

    @Override
    public Optional<CarModel> getById(int id) throws SQLException {
        return Optional.empty();
    }
}
