package dao.impl.orm.jpa;

import entity.order.Address;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

public class AddressDao implements dao.interfaces.AddressDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public AddressDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }

    @Override
    public Optional<Address> getAddress(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Address> getAddress(String title) throws SQLException {
        return Optional.empty();
    }
}
