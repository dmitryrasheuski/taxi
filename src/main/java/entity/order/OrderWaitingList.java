package entity.order;

import entity.user.User;

import java.util.*;

public class OrderWaitingList {
    private static final List<Order> list = new LinkedList<>();
    private static final OrderWaitingList INSTANCE = new OrderWaitingList();

    private OrderWaitingList() {}
    public static OrderWaitingList getInstance(){
        return INSTANCE;
    }

    public boolean addOrder(Order order) {
        return list.add(order);
    }
    public void removeOrder(Order order) {
        list.remove(order);
    }
    public Optional<Order> getOrderByPassenger(User passenger) {
        return list.stream()
                .filter( (order) -> order.getPassenger().equals(passenger) )
                .findFirst();
    }
    public List<Order> getOrderList() {
        return new ArrayList<>(list);
    }
}
