package service.interfaces.order;

import entity.order.Order;

public interface IAddOrder {
    boolean addOrderToWaitingList(Order order);
}
