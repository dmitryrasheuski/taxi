package service.impl.order;

import dao.interfaces.OrderDao;
import entity.car.Car;
import entity.order.Order;
import entity.order.OrderWaitingList;
import entity.user.User;
import service.interfaces.order.*;

import java.util.List;

public class OrderService implements IOrderCreating, IOrderServing, IOrderClosing, ITripListProviding, IOrderWaitingListProviding {
    private OrderDao dao;
    private OrderWaitingList orderWaitingList;

    public OrderService(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public void closeOrder(Order order) {

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
}
