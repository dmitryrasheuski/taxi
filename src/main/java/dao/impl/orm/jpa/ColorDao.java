package dao.impl.orm.jpa;

import entity.car.Color;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

public class ColorDao implements dao.interfaces.ColorDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public ColorDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }
    @Override
    public Optional<Integer> addColor(String title) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Color> getByTitle(String title) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Color getOrElseAddAndGet(String title) throws SQLException {
        return null;
    }

    @Override
    public Optional<Color> getById(int id) throws SQLException {
        return Optional.empty();
    }
}
