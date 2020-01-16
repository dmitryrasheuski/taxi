package entity.order;

import entity.user.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class OrderWaitingList {
    private static final Map<User, Order> map = new HashMap<>();
    private static final OrderWaitingList INSTANCE = new OrderWaitingList();

    private OrderWaitingList() {}
    public static OrderWaitingList getInstance(){
        return INSTANCE;
    }

    public boolean addOrder(Order order) {
        return map.put(order.getPassenger(), order) != null;
    }
    public void removeOrder(Order order) {
        map.remove(order.getPassenger());
    }
    public Optional<Order> getOrderByPassenger(User passenger) {
        return Optional.ofNullable( map.get(passenger) );
    }
}
