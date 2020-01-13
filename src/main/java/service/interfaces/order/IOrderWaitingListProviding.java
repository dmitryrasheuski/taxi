package service.interfaces.order;

import entity.order.Order;

import java.util.List;

public interface IOrderWaitingListProviding {
    List<Order> getOrderWaitingList();
}
