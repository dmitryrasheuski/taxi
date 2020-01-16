package service.impl.order;

import dao.interfaces.OrderDao;
import entity.car.Car;
import entity.order.Order;
import entity.order.OrderWaitingList;
import entity.user.User;
import service.interfaces.order.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderService implements IOrderCreating, IOrderServing, IOrderClosing, ITripListProviding, IOrderWaitingListProviding {
    private OrderDao dao;
    private OrderWaitingList orderWaitingList;

    public OrderService(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean closeOrder(Order order) {
        if ( ! validateOrder(order) ) return false;

        long id = 0L;
        try {
            id = dao.addOrder(order).orElse(-1L);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id > 0;

    }

    @Override
    public boolean createOrder(Order order) {
        if (order == null || order.getPassenger() == null || order.getFrom() == null || order.getWhere() == null ) return false;

        return orderWaitingList.addOrder(order);

    }

    @Override
    public Order servePassenger(User passenger, Car car) {
        return null;
    }

    @Override
    public List<Order> getOrderWaitingList() {
        return null;
    }

    @Override
    public List<Order> getTripList(User user) {
        return null;
    }

    private boolean validateOrder(Order order) {
        if (order == null) return false;
        if (order.getPassenger() == null) return false;
        if (order.getCar() == null) return false;
        if (order.getFrom() == null) return false;
        if (order.getWhere() == null) return false;

        return true;

    }
}
