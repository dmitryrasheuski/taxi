package dao.impl.orm.jpa;

import entity.order.Order;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderDao implements dao.interfaces.OrderDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public OrderDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }

    @Override
    public Optional<Long> addOrder(Order order) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> deleteOrder(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Order>> getListByPassengerId(long idUser) throws SQLException {
        return Optional.empty();
    }
}
