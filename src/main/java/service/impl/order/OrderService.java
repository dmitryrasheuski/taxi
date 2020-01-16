package service.impl.order;

import dao.interfaces.OrderDao;
import entity.car.Car;
import entity.order.Order;
import entity.order.OrderWaitingList;
import entity.user.User;
import lombok.extern.log4j.Log4j;
import service.interfaces.order.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class OrderService implements IOrderCreating, IOrderServing, IOrderClosing, ITripListProviding, IOrderWaitingListProviding {
    private OrderDao dao;
    private OrderWaitingList orderWaitingList;

    public OrderService(OrderDao dao, OrderWaitingList list) {
        this.dao = dao;
        this.orderWaitingList = list;
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
        if (passenger == null || car == null) return null;

        Order order = orderWaitingList.getOrderByPassenger(passenger).orElse(null);
        if (order == null) return null;

        orderWaitingList.removeOrder(order);
        order.setCar(car);
        return order;

    }

    @Override
    public List<Order> getOrderWaitingList() {
        return orderWaitingList.getOrderList();
    }

    @Override
    public List<Order> getTripList(User user) {
        if (user == null) return new ArrayList<>();

        List<Order> list = new ArrayList<>();
        try {
            list = dao.getListByPassengerId(user.getId()).orElse(new ArrayList<>());
        } catch (SQLException ex) {
            log.error("Finding order list by userId" + user.getId() , ex);
        }

        return list;

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
