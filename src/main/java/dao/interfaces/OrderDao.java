package dao.interfaces;

import appException.dao.AppSqlException;
import entity.order.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Long> addOrder(Order order) throws SQLException, AppSqlException;
    Optional<Integer> deleteOrder(long id) throws SQLException, AppSqlException;
    Optional<List<Order>> getListByIdUser(long idUser) throws SQLException, AppSqlException;
}
