package service.interfaces.order;

import entity.order.Order;

public interface IServeOrder {
    void serveOrder(Order order);
    void closeOrder(Order order);
}
