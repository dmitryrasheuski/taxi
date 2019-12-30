package dao.interfaces;

import entity.order.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Long> addOrder(Order order) throws SQLException;
    Optional<Integer> deleteOrder(long id) throws SQLException;
    Optional<List<Order>> getListByPassengerId(long idUser) throws SQLException;
}
