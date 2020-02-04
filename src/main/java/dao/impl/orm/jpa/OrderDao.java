package dao.impl.orm.jpa;

import entity.order.Order;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class OrderDao extends AbstractDao<Order> implements dao.interfaces.OrderDao {
    private static final String getList = "SELECT o FROM Order o";

    OrderDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Long> addOrder(Order order) throws SQLException {
        return addEntity(order).map(Order::getId);
    }

    @Override
    public Optional<Integer> deleteOrder(long id) throws SQLException {

        boolean success = removeEntity(Order.class, id);

        return success ?
                Optional.of(1) :
                Optional.empty();
    }

    @Override
    public Optional<List<Order>> getListByPassengerId(long idUser) throws SQLException {

        List<Order> list = getEntities( getList , new LinkedHashMap<>(1) );

        return Optional.ofNullable(list);
    }
}
