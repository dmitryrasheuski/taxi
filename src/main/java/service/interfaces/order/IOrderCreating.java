package service.interfaces.order;

import entity.order.Order;

public interface IOrderCreating {
    boolean createOrder(Order order);
}
