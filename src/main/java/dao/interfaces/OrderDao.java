package dao.interfaces;

import appException.dao.AppSqlException;
import entity.order.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    long addOrder(Order order) throws SQLException, AppSqlException;
    void deleteOrder(long id) throws SQLException, AppSqlException;

    List<Order> getListByIdUser(long idUser) throws SQLException, AppSqlException;
}
